package com.circuitqueest.app.screenshots

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.Question
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.ui.screens.HomeScreen
import com.circuitqueest.app.ui.screens.LessonScreen
import com.circuitqueest.app.ui.screens.QuizScreen
import com.circuitqueest.app.ui.screens.ResultScreen
import com.circuitqueest.app.ui.theme.CircuitQueestTheme
import com.circuitqueest.app.viewmodel.HomeViewModel
import com.circuitqueest.app.viewmodel.LessonViewModel
import com.circuitqueest.app.viewmodel.QuizViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Generates Google Play store screenshots; not a correctness test. Excluded from the
 * normal instrumented run (build.yml passes notPackage) and run by screenshots.yml,
 * which pulls the PNGs from the app's external files dir.
 */
@RunWith(AndroidJUnit4::class)
class StoreScreenshots {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var database: AppDatabase
    private lateinit var repository: ProgressRepository
    private val topics = TopicsService.allTopics

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = ProgressRepository(
            database.progressDao(), database.quizResultDao(), database.missedQuestionDao()
        )
        // A player a few quests in: four passed, the fifth lesson read, two questions to review.
        runBlocking {
            listOf(7, 6, 7, 5).forEachIndexed { i, score ->
                repository.markLessonCompleted(topics[i].id)
                repository.recordQuizResult(topics[i].id, score, 7)
            }
            repository.markLessonCompleted(topics[4].id)
            repository.recordAnswer(topics[1].id, topics[1].quiz.questions[2].id, correct = false)
            repository.recordAnswer(topics[3].id, topics[3].quiz.questions[4].id, correct = false)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun show(blueprint: Boolean = false, content: @Composable () -> Unit) {
        composeTestRule.setContent { CircuitQueestTheme(blueprintMode = blueprint) { content() } }
        composeTestRule.waitForIdle()
    }

    private fun save(name: String) {
        composeTestRule.waitForIdle()
        val bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap()
        val dir = File(context.getExternalFilesDir(null), "screenshots").apply { mkdirs() }
        File(dir, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    }

    @Test
    fun s1_questMap() {
        show { HomeScreen(viewModel = HomeViewModel(repository), onTopicClick = {}) }
        save("1-quest-map")
    }

    @Test
    fun s2_lesson() {
        val vm = LessonViewModel(repository, SavedStateHandle(mapOf("topicId" to topics[4].id)))
        show { LessonScreen(viewModel = vm, onBack = {}, onStartQuiz = {}) }
        save("2-lesson")
    }

    @Test
    fun s3_quizCorrectAnswer() {
        val topic = topics.first { t -> t.quiz.questions.first() is Question.MultipleChoice }
        val q = topic.quiz.questions.first() as Question.MultipleChoice
        val vm = QuizViewModel(repository, SavedStateHandle(mapOf("topicId" to topic.id)))
        show { QuizScreen(viewModel = vm, onBack = {}, onQuizComplete = { _, _, _, _ -> }) }
        composeTestRule.onAllNodesWithText(q.options[q.correctIndex]).onFirst().performClick()
        composeTestRule.onNodeWithText("Submit Answer").performScrollTo().performClick()
        save("3-quiz-feedback")
    }

    @Test
    fun s4_result() {
        show {
            ResultScreen(
                topicId = topics[5].id, score = 6, totalQuestions = 7, xpEarned = 160,
                onRetry = {}, onHome = {}, onReviewMistakes = {}, onNextLesson = {}
            )
        }
        save("4-result")
    }

    @Test
    fun s5_review() {
        val vm = QuizViewModel(
            repository,
            SavedStateHandle(mapOf("topicId" to QuizViewModel.REVIEW_ALL, "review" to true))
        )
        show { QuizScreen(viewModel = vm, onBack = {}, onQuizComplete = { _, _, _, _ -> }) }
        save("5-review")
    }

    @Test
    fun s6_blueprintMode() {
        show(blueprint = true) {
            HomeScreen(viewModel = HomeViewModel(repository), onTopicClick = {}, blueprintMode = true)
        }
        save("6-blueprint")
    }
}
