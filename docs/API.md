# API Documentation

## Public APIs Reference

Complete documentation of public interfaces, classes, and methods available for developers extending CircuitQuEEst.

## ViewModel APIs

### HomeViewModel
Manages the home screen state including topic list and user progress.

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val topicsService: TopicsService,
    savedStateHandle: SavedStateHandle
) : ViewModel()
```

#### Properties
```kotlin
// All topics available for learning
val allTopics: StateFlow<List<Topic>>

// User progress for each topic
val userProgress: StateFlow<Map<String, Progress?>>

// Total XP earned by user
val totalXP: StateFlow<Int>

// Currently selected topic
val selectedTopic: StateFlow<Topic?>

// Loading state
val isLoading: StateFlow<Boolean>

// Error messages
val errorMessage: StateFlow<String?>
```

#### Methods
```kotlin
// Select a topic to view details
fun selectTopic(topicId: String)

// Refresh progress data
suspend fun refreshProgress()

// Clear error message
fun clearError()
```

#### Example Usage
```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val topics by viewModel.allTopics.collectAsState()
    val progress by viewModel.userProgress.collectAsState()
    
    LazyColumn {
        items(topics) { topic ->
            TopicCard(
                topic = topic,
                progress = progress[topic.id],
                onClick = { viewModel.selectTopic(topic.id) }
            )
        }
    }
}
```

---

### QuizViewModel
Manages quiz state and scoring.

```kotlin
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val topicsService: TopicsService,
    savedStateHandle: SavedStateHandle
) : ViewModel()
```

#### Properties
```kotlin
// Current quiz questions
val currentQuiz: StateFlow<Quiz>

// Current question index
val currentQuestionIndex: StateFlow<Int>

// User's selected answer for current question
val selectedAnswer: StateFlow<String?>

// Total score so far
val currentScore: StateFlow<Int>

// Quiz completion state
val isQuizComplete: StateFlow<Boolean>

// Percentage of quiz completed
val progressPercentage: StateFlow<Float>
```

#### Methods
```kotlin
// Start quiz for given topic
suspend fun startQuiz(topicId: String)

// Select an answer for current question
fun selectAnswer(answer: String)

// Move to next question
suspend fun nextQuestion()

// Submit quiz and calculate score
suspend fun submitQuiz(): QuizResult

// Restart current quiz
suspend fun restartQuiz()
```

#### Example Usage
```kotlin
@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val quiz by viewModel.currentQuiz.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selected by viewModel.selectedAnswer.collectAsState()
    
    val question = quiz.questions[currentIndex]
    
    Column {
        Text(question.text)
        question.options.forEach { option ->
            Button(
                onClick = { viewModel.selectAnswer(option) },
                selected = selected == option
            ) {
                Text(option)
            }
        }
    }
}
```

---

### LessonViewModel
Manages lesson content display.

```kotlin
@HiltViewModel
class LessonViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val topicsService: TopicsService,
    savedStateHandle: SavedStateHandle
) : ViewModel()
```

#### Properties
```kotlin
// Current lesson being displayed
val currentLesson: StateFlow<Lesson>

// Lesson content sections
val sections: StateFlow<List<Section>>

// Current section index
val currentSectionIndex: StateFlow<Int>

// Is lesson content loaded
val isLoaded: StateFlow<Boolean>
```

#### Methods
```kotlin
// Load lesson for topic
suspend fun loadLesson(topicId: String)

// Move to next section
fun nextSection()

// Move to previous section
fun previousSection()

// Start quiz for this lesson
suspend fun startQuiz()
```

---

## Repository APIs

### ProgressRepository
Central data access point for user progress.

```kotlin
@Inject
class ProgressRepository(
    private val progressDao: ProgressDao,
    private val quizResultDao: QuizResultDao
)
```

#### Methods
```kotlin
// Get progress for specific topic
suspend fun getProgress(topicId: String): Progress?

// Get all user progress across topics
suspend fun getAllProgress(): List<Progress>

// Save quiz completion
suspend fun saveQuizResult(result: QuizResult)

// Get all quiz attempts for topic
suspend fun getQuizResults(topicId: String): List<QuizResult>

// Update best score for topic
suspend fun updateBestScore(topicId: String, score: Int)

// Get total XP earned
suspend fun getTotalXP(): Int

// Reset all progress (dangerous!)
suspend fun resetProgress()
```

#### Data Classes
```kotlin
data class Progress(
    val topicId: String,          // PK
    val bestScore: Int,           // 0-100
    val totalQuestions: Int,      // Total questions attempted
    val lastAttempt: Long = 0     // Timestamp of last quiz
)

data class QuizResult(
    val id: Int = 0,              // Auto-incremented PK
    val topicId: String,          // FK to topic
    val score: Int,               // 0-100
    val totalQuestions: Int,      // Number of questions
    val timestamp: Long           // When quiz was completed
)
```

#### Example Usage
```kotlin
// In ViewModel
viewModelScope.launch {
    val progress = repository.getProgress("ohms_law")
    if (progress != null) {
        updateUI(progress)
    }
}

// Save quiz results
val result = QuizResult(
    topicId = "ohms_law",
    score = 85,
    totalQuestions = 10,
    timestamp = System.currentTimeMillis()
)
repository.saveQuizResult(result)
```

---

## DAO APIs

### ProgressDao
Direct database access for Progress entity.

```kotlin
@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: Progress)
    
    @Query("SELECT * FROM Progress WHERE topicId = :topicId")
    suspend fun getProgress(topicId: String): Progress?
    
    @Query("SELECT * FROM Progress")
    suspend fun getAllProgress(): List<Progress>
    
    @Query("DELETE FROM Progress WHERE topicId = :topicId")
    suspend fun deleteProgress(topicId: String)
    
    @Query("DELETE FROM Progress")
    suspend fun deleteAll()
}
```

---

### QuizResultDao
Direct database access for QuizResult entity.

```kotlin
@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResult)
    
    @Query("SELECT * FROM QuizResult WHERE topicId = :topicId")
    suspend fun getResults(topicId: String): List<QuizResult>
    
    @Query("SELECT AVG(score) FROM QuizResult WHERE topicId = :topicId")
    suspend fun getAverageScore(topicId: String): Int?
    
    @Query("DELETE FROM QuizResult WHERE topicId = :topicId")
    suspend fun deleteResults(topicId: String)
}
```

---

## Service APIs

### TopicsService
Provides access to all educational content.

```kotlin
@Inject
class TopicsService {
    // Get all topics sorted by order
    fun getAllTopics(): List<Topic>
    
    // Get specific topic
    fun getTopic(topicId: String): Topic?
    
    // Get topics by category
    fun getTopicsByCategory(category: String): List<Topic>
}
```

#### Topic Data Structure
```kotlin
data class Topic(
    val id: String,              // Unique identifier
    val name: String,            // Display name
    val description: String,     // Brief description
    val icon: String,            // Icon reference
    val lessons: List<Lesson>,  // Course material
    val quiz: Quiz,              // Quiz questions
    val category: String         // Topic category
)

data class Lesson(
    val title: String,
    val sections: List<Section>
)

data class Section(
    val title: String,
    val content: String          // HTML or markdown
)

data class Quiz(
    val questions: List<Question>
)

data class Question(
    val text: String,
    val options: List<String>,   // Answer choices
    val correctAnswer: String    // For validation
)
```

#### Example Usage
```kotlin
val topicsService: TopicsService = get()  // Via DI
val allTopics = topicsService.getAllTopics()
val topic = topicsService.getTopic("ohms_law")

// Use in UI
Column {
    allTopics.forEach { topic ->
        Text(topic.name)
        Text(topic.description)
    }
}
```

---

## Navigation APIs

### NavGraph Routes
Type-safe navigation routes.

```kotlin
// Home screen - no arguments
val HOME = "home"

// Lesson screen - requires topicId
val LESSON = "lesson/{topicId}"

// Quiz screen - requires topicId
val QUIZ = "quiz/{topicId}"

// Result screen - requires topicId and score
val RESULT = "result/{topicId}/{score}"
```

#### Navigation Example
```kotlin
val navController = rememberNavController()

Button(onClick = {
    navController.navigate("lesson/ohms_law")
}) {
    Text("Start Lesson")
}

// In NavHost
composable("lesson/{topicId}") { backStackEntry ->
    val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
    LessonScreen(topicId = topicId)
}
```

---

## Utility Functions

### QuizScoring
```kotlin
object QuizScoring {
    // Calculate score percentage
    fun calculateScore(correctAnswers: Int, totalQuestions: Int): Int {
        return if (totalQuestions > 0) {
            (correctAnswers * 100) / totalQuestions
        } else {
            0
        }
    }
    
    // Calculate XP reward based on score
    fun calculateXP(score: Int): Int {
        return when {
            score >= 90 -> 100
            score >= 80 -> 75
            score >= 70 -> 50
            score >= 60 -> 25
            else -> 10
        }
    }
}
```

#### Example
```kotlin
val score = QuizScoring.calculateScore(8, 10)  // Returns 80
val xp = QuizScoring.calculateXP(score)        // Returns 75
```

---

## Composable Components

### TopicCard
Reusable component for displaying topic in list.

```kotlin
@Composable
fun TopicCard(
    topic: Topic,
    progress: Progress?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Parameters:**
- `topic` - Topic to display
- `progress` - User progress for topic (nullable)
- `onClick` - Callback when tapped
- `modifier` - Compose modifier (optional)

---

### QuestionCard
Display quiz question with answer options.

```kotlin
@Composable
fun QuestionCard(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier = Modifier
)
```

---

### ProgressBar
Display topic progress visualization.

```kotlin
@Composable
fun ProgressBar(
    progress: Progress,
    modifier: Modifier = Modifier
)
```

Shows:
- Best score percentage
- XP earned
- Quiz attempts count

---

## Dependency Injection

### Using Hilt
All dependencies are injected via Hilt. No manual factory needed.

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val topicsService: TopicsService
) : ViewModel()
```

### Module Providers
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProgressRepository(
        progressDao: ProgressDao,
        quizResultDao: QuizResultDao
    ): ProgressRepository = ProgressRepository(progressDao, quizResultDao)
}
```

---

## Database Access

### Room Database
```kotlin
@Database(
    entities = [Progress::class, QuizResult::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun quizResultDao(): QuizResultDao
}
```

### Creating Instance
```kotlin
val database = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "circuitqueest-db"
).build()
```

---

## Error Handling

### Exception Types
```kotlin
// Thrown when topic not found
class TopicNotFoundException(topicId: String) : Exception()

// Thrown when quiz validation fails
class QuizValidationException(message: String) : Exception()

// Generic app exception
class CircuitQuestException(message: String) : Exception()
```

### Handling Errors
```kotlin
try {
    val topic = topicsService.getTopic(topicId)
} catch (e: TopicNotFoundException) {
    showError("Topic not found")
} catch (e: Exception) {
    showError("An error occurred: ${e.message}")
}
```

---

## Best Practices

1. **Use Hilt for DI** - Never instantiate services/repositories manually
2. **Use StateFlow** - For observable state in ViewModels
3. **Use Coroutines** - For async operations in viewModelScope
4. **Use Type-Safe Navigation** - Leverage NavGraph route definitions
5. **Handle Null Safety** - Always check nullable Progress/Topic values
6. **Test Your Code** - Write tests for repository and ViewModel changes

---

## Versioning

This API documentation matches CircuitQuEEst v1.0.0

For updates, check:
- [CHANGELOG.md](../CHANGELOG.md)
- GitHub releases

---

**Related Documentation:**
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design details
- [DEVELOPMENT.md](DEVELOPMENT.md) - Development setup
- [TESTING.md](TESTING.md) - Testing guide
