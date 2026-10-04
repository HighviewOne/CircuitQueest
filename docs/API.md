# API Documentation

Reference for the app's internal Kotlin APIs, as of v2.4. Everything lives under
`app/src/main/java/com/circuitqueest/app/`. The app is offline-only: there is no network API.

```
UI (Compose screens) ──collectAsStateWithLifecycle──▶ ViewModels (StateFlow)
                                                        │
                       TopicsService (static content) ◀─┤
                                                        ▼
                                            ProgressRepository ──▶ Room DAOs ──▶ circuitqueest_db
```

---

## Content

Lesson and quiz content is compiled into the app as Kotlin singleton objects
(`data/content/*Content.kt`), one per topic.

### Data model (`data/content/TopicContent.kt`)

```kotlin
data class Topic(
    val id: String,          // stable key, e.g. "ohms_law" — used in routes and the database
    val title: String,
    val subtitle: String,
    val icon: String,        // emoji shown in the card badge
    val order: Int,          // quest number = position in the unlock path (see TopicCategories)
    val lesson: Lesson,
    val quiz: Quiz
)

data class Lesson(val title: String, val sections: List<LessonSection>)

data class LessonSection(
    val heading: String,
    val content: String,
    val formula: String? = null,   // rendered in a FormulaTile
    val keyPoint: String? = null   // rendered as a "KEY INSIGHT" callout
)

data class Quiz(val title: String, val questions: List<Question>)

sealed class Question {
    abstract val id: String
    abstract val questionText: String
    abstract val explanation: String
    abstract val points: Int        // default 1

    data class MultipleChoice(
        /* id, questionText, */ val options: List<String>, val correctIndex: Int,
        /* explanation, points = 1 */
    ) : Question()

    data class NumericInput(
        /* id, questionText, */ val correctAnswer: Double,
        val tolerance: Double = 0.01,   // absolute: |answer - correctAnswer| <= tolerance
        val unit: String = "",          // shown next to the input, e.g. "Ω"
        /* explanation, points = 1 */
    ) : Question()
}
```

### `TopicsService` (`data/content/TopicsService.kt`)

```kotlin
object TopicsService {
    val allTopics: List<Topic>   // all 42 topics, sorted by `order`
}
```

Look a topic up with `TopicsService.allTopics.find { it.id == topicId }`.

### `TopicCategories` (`data/content/TopicCategories.kt`)

```kotlin
data class TopicCategory(val name: String, val icon: String, val topicIds: List<String>)

object TopicCategories {
    val categories: List<TopicCategory>                 // 9 categories, in quest-map order
    fun categoryFor(topicId: String): TopicCategory?
}
```

The categories, top to bottom, define the **unlock order**. Each topic's `order` must equal its
index in `categories.flatMap { it.topicIds }`; `HomeViewModelTest.unlockOrder_followsCategoryLayout`
fails otherwise.

---

## Progress rules — `QuizScoring` (`util/QuizScoring.kt`)

All scoring and XP rules live here; nothing else hard-codes them.

```kotlin
object QuizScoring {
    const val PASS_PERCENT = 60        // minimum % to pass and unlock the next topic
    const val XP_PER_POINT = 10
    const val FIRST_PASS_BONUS = 100
    const val LESSON_XP = 50

    fun checkAnswer(question: Question, answer: Any?): Boolean
    fun percentage(score: Int, totalQuestions: Int): Int            // 0 when totalQuestions == 0
    fun isPassing(score: Int, totalQuestions: Int): Boolean
    fun calculateXp(score: Int, previousBest: Int, isFirstPass: Boolean): Int
}
```

- `checkAnswer`: `MultipleChoice` expects an `Int` index; `NumericInput` accepts any `Number`
  within `tolerance`. Anything else returns `false`.
- `calculateXp` pays only for points **above the previous best**, plus `FIRST_PASS_BONUS` on the
  first passing attempt. Retakes at or below the best score earn 0, so a topic's lifetime quiz XP is
  `best × 10 (+100 once passed)`.

```kotlin
QuizScoring.calculateXp(score = 7, previousBest = 0, isFirstPass = true)   // 170
QuizScoring.calculateXp(score = 8, previousBest = 7, isFirstPass = false)  // 10
QuizScoring.calculateXp(score = 7, previousBest = 7, isFirstPass = false)  // 0
```

---

## Repository — `ProgressRepository` (`data/repository/ProgressRepository.kt`)

The only class that writes progress. Hilt provides a singleton; inject it, don't construct it
(tests construct it directly with DAOs from an in-memory database).

```kotlin
class ProgressRepository(progressDao: ProgressDao, quizResultDao: QuizResultDao) {
    fun getAllProgress(): Flow<List<TopicProgress>>
    fun getProgress(topicId: String): Flow<TopicProgress?>
    fun getTotalXp(): Flow<Int>
    fun getQuizResults(topicId: String): Flow<List<QuizResult>>   // newest first

    suspend fun markLessonCompleted(topicId: String)               // +LESSON_XP the first time only
    suspend fun recordQuizResult(topicId: String, score: Int, totalQuestions: Int): Int
    suspend fun saveQuizResult(topicId: String, score: Int, totalQuestions: Int) // = recordQuizResult, ignoring the XP
}
```

`recordQuizResult` inserts a `QuizResult`, then updates the topic's `TopicProgress`:
`bestScore = max(best, score)`; `quizCompleted` becomes `true` only on a passing attempt (and never
reverts); XP is added per `QuizScoring.calculateXp`. It **returns the XP awarded**, which the quiz
passes to the result screen.

Writes are serialized with a `Mutex`, so concurrent updates to the same topic can't lose XP.

---

## Database

### `AppDatabase` (`data/db/AppDatabase.kt`)

```kotlin
@Database(entities = [TopicProgress::class, QuizResult::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao
    companion object { const val DATABASE_NAME = "circuitqueest_db" }
}
```

- **Never change `DATABASE_NAME`**: it would orphan every player's saved progress.
- Schemas are exported to `app/schemas/`. To change an entity, bump `version`, add a `Migration`,
  and test it with `MigrationTestHelper` (`room-testing` is already an `androidTest` dependency).

### Entities (`data/db/entity/`)

```kotlin
@Entity(tableName = "topic_progress")
data class TopicProgress(
    @PrimaryKey val topicId: String,
    val lessonCompleted: Boolean = false,
    val quizCompleted: Boolean = false,   // true once any attempt passed — drives unlocking
    val bestScore: Int = 0,
    val totalQuestions: Int = 0,
    val xpEarned: Int = 0,                // lesson + quiz XP for this topic
    val lastAccessedTimestamp: Long = 0L
)

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicId: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)
```

### DAOs (`data/db/dao/`)

```kotlin
@Dao interface ProgressDao {
    fun getProgress(topicId: String): Flow<TopicProgress?>
    suspend fun getProgressOnce(topicId: String): TopicProgress?
    fun getAllProgress(): Flow<List<TopicProgress>>
    @Insert(onConflict = REPLACE) suspend fun upsertProgress(progress: TopicProgress)
    fun getTotalXp(): Flow<Int>                       // SUM(xpEarned), 0 when empty
}

@Dao interface QuizResultDao {
    @Insert suspend fun insertResult(result: QuizResult)
    fun getResultsForTopic(topicId: String): Flow<List<QuizResult>>   // ORDER BY timestamp DESC
    fun getBestScore(topicId: String): Flow<Int?>                      // MAX(score)
}
```

---

## Dependency injection

`CircuitQuestApplication` is annotated `@HiltAndroidApp`; `MainActivity` is `@AndroidEntryPoint`.
`data/di/RepositoryModule.kt` (installed in `SingletonComponent`) provides:

```kotlin
@Provides @Singleton fun provideDatabase(@ApplicationContext context: Context): AppDatabase
@Provides @Singleton fun provideProgressRepository(database: AppDatabase): ProgressRepository
```

ViewModels are `@HiltViewModel` and obtained in composables with `hiltViewModel()`.

---

## ViewModels (`viewmodel/`)

All state is exposed as `StateFlow`; screens read it with `collectAsStateWithLifecycle()`.
`LessonViewModel` and `QuizViewModel` read `topicId` from the navigation arguments via
`SavedStateHandle`.

### `HomeViewModel`

```kotlin
data class TopicState(val topic: Topic, val progress: TopicProgress?, val isLocked: Boolean)
data class CategoryState(val category: TopicCategory, val topics: List<TopicState>)

val topicStates: StateFlow<List<TopicState>>          // in unlock order
val categorizedTopics: StateFlow<List<CategoryState>> // grouped for the quest map
val totalXp: StateFlow<Int>
```

Lock rule: the first topic is always open; any other topic is locked unless the previous topic's
quiz was passed, **or** the topic itself already has progress (so saves made under the pre-2.4
unlock order are never re-locked).

### `LessonViewModel`

```kotlin
val topic: StateFlow<Topic?>          // null for an unknown id → LessonScreen shows QuestNotFound
val lessonCompleted: StateFlow<Boolean>
fun markLessonComplete()
```

### `QuizViewModel`

```kotlin
data class QuizState(
    val topicId: String = "", val quizTitle: String = "",
    val currentIndex: Int = 0, val score: Int = 0, val totalQuestions: Int = 0,
    val xpEarned: Int = 0             // set when the attempt is saved
)
data class QuizFeedback(val isCorrect: Boolean, val explanation: String)

val quizState: StateFlow<QuizState>
val currentQuestion: StateFlow<Question?>
val feedback: StateFlow<QuizFeedback?>   // non-null after answering, until nextQuestion()
val quizComplete: StateFlow<Boolean>     // true after the attempt is saved

fun answerMultipleChoice(selectedIndex: Int)
fun answerNumeric(value: Double?)        // null (unparseable input) counts as wrong
fun nextQuestion()
```

- Answers are ignored once `feedback` is set; `nextQuestion()` is ignored until it is.
- On the last question, `nextQuestion()` saves via `ProgressRepository.recordQuizResult` exactly
  once (repeat taps are ignored), stores the awarded XP in `quizState.xpEarned`, then sets
  `quizComplete`.
- `totalQuestions == 0` (unknown topic) → `QuizScreen` shows `QuestNotFound`.

---

## Navigation (`navigation/NavGraph.kt`)

```kotlin
object Routes {
    const val HOME = "home"
    const val LESSON = "lesson/{topicId}"
    const val QUIZ = "quiz/{topicId}"
    const val RESULT = "result/{topicId}/{score}/{total}/{xp}"

    fun lesson(topicId: String): String
    fun quiz(topicId: String): String
    fun result(topicId: String, score: Int, total: Int, xp: Int): String
}

@Composable fun CircuitQueestNavGraph(onToggleBlueprint: () -> Unit = {}, blueprintMode: Boolean = false)
```

Always build routes with the `Routes` helpers:

```kotlin
navController.navigate(Routes.lesson("ohms_law"))
navController.navigate(Routes.result(topicId, score, total, xp)) { popUpTo(Routes.HOME) }
```

The graph is wrapped in `SharedTransitionLayout`. Destinations expose
`LocalNavSharedTransitionScope` / `LocalNavAnimatedVisibilityScope` so `TopicCard` and the lesson
hero can share the bounds key `"topic_card_$topicId"`.

---

## Screens (`ui/screens/`)

| Screen | Signature | Notes |
|---|---|---|
| `HomeScreen` | `(viewModel: HomeViewModel, onTopicClick: (String) -> Unit, onToggleBlueprint: () -> Unit = {}, blueprintMode: Boolean = false)` | Collapsible categories (expanded set survives rotation), search via `Topic.matchesSearch(query)`: title, subtitle, section headings, formulas |
| `LessonScreen` | `(viewModel: LessonViewModel, onBack: () -> Unit, onStartQuiz: (String) -> Unit)` | Sticky CTA fades in past the hero (or immediately if nothing scrolls); inset above the nav bar |
| `QuizScreen` | `(viewModel: QuizViewModel, onBack: () -> Unit, onQuizComplete: (topicId: String, score: Int, total: Int, xpEarned: Int) -> Unit)` | Confirms before leaving once an answer exists (close button and system back) |
| `ResultScreen` | `(topicId: String, score: Int, totalQuestions: Int, xpEarned: Int, onRetry: (String) -> Unit, onHome: () -> Unit, onNextLesson: ((String) -> Unit)? = null)` | Shows the XP passed in, never recomputes it; "Up next" only when passed |

---

## Components (`ui/components/`)

| Composable | Signature |
|---|---|
| `TopicCard` | `(topicId, topicNumber: Int, title, subtitle, topicIcon, isLocked, isCurrent, lessonCompleted, quizCompleted, quizScore: Int?, totalQuestions: Int?, accentColor: Color, onClick, modifier)`. Locked cards shake instead of calling `onClick` and expose a "Locked" state description |
| `CategoryHeader` | `(imageVector, name, completedCount, totalCount, categoryXp, accentColor, isExpanded, onClick, modifier)` |
| `XpBar` | `(totalXp: Int, modifier)`. Level = `totalXp / 500 + 1` |
| `StatusChip` | `(status: ChipStatus, label: String? = null, modifier)` with `ChipStatus { DONE, IN_PROGRESS, LOCKED }` |
| `TopicGlyphBadge` | `(imageVector: ImageVector? = null, label: String = "", accentColor = CqBlue, size = 40.dp, modifier)` |
| `FormulaTile` | `(formula: String, modifier)`. Gold-bracketed formula, used by lesson sections |
| `MultipleChoiceQuestion` | `(questionText, options, questionNumber, correctIndex, isSubmitted, onAnswer: (Int) -> Unit, modifier)` |
| `NumericInputQuestion` | `(questionText, unit, questionNumber, isSubmitted, onAnswer: (Double?) -> Unit, modifier)`. Parses with `parseNumericAnswer`, which accepts `,` as the decimal separator |
| `QuestionCard` | `(questionNumber, questionText, modifier)` |
| `AnswerFeedback` | `(isCorrect, explanation, onNext, modifier)` |
| `QuestNotFound` | `(onBack: () -> Unit)` |
| `XpProgressBar`, `QuizScoreDisplay`, `FormulaDisplay` | `(currentXp, label = "Total XP", modifier)`, `(score, total, modifier)`, `(formula, modifier)`. Legacy, not used by current screens (`FormulaDisplay` just wraps `FormulaTile`) |

Modifiers (`ModifierExtensions.kt`): `Modifier.goldBrackets()` (public) and
`Modifier.dashedBorder(width, color, cornerRadius)` (internal).

### Theming (`ui/theme/`)

`CircuitQueestTheme(blueprintMode: Boolean = false, content)` provides the Material color scheme and
`LocalCqPalette`. Use `LocalCqPalette.current` (`bg`, `bg2`, `surface`, `surface2`, `border`,
`borderStrong`, `track`) for structural colors and the `Cq*` constants (`CqBlue`, `CqGold`,
`CqGreen`, `CqRed`, `CqText*`) for accents. Never hard-code colors; the PR template checks this.
Fonts: `SpaceGrotesk`, `JetBrainsMono`, `MonoLabel` (`Type.kt`); spacing and radii: `Spacing.s4…s64`,
`Radius.sm…xl`.

---

## Versioning

This document tracks the code on `master`. When you change a public signature above, update it in
the same PR.
