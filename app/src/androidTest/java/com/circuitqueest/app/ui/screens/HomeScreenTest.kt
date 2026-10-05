package com.circuitqueest.app.ui.screens

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.circuitqueest.app.data.content.TopicsService
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.ui.theme.CircuitQueestTheme
import com.circuitqueest.app.viewmodel.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var database: AppDatabase
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: ProgressRepository

    private val ohmsLaw = TopicsService.allTopics.first { it.id == "ohms_law" }
    private val mosfets = TopicsService.allTopics.first { it.id == "mosfets" }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = ProgressRepository(
            database.progressDao(), database.quizResultDao(), database.missedQuestionDao()
        )
        viewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
        database.close()
    }

    /** Mirrors MainActivity: blueprint state hoisted above the theme. */
    private fun showHome(onTopicClick: (String) -> Unit = {}, onReview: () -> Unit = {}) {
        composeTestRule.setContent {
            var blueprint by remember { mutableStateOf(false) }
            CircuitQueestTheme(blueprintMode = blueprint) {
                HomeScreen(
                    viewModel = viewModel,
                    onTopicClick = onTopicClick,
                    onToggleBlueprint = { blueprint = !blueprint },
                    blueprintMode = blueprint,
                    onReview = onReview
                )
            }
        }
    }

    @Test
    fun blueprintToggle_switchesChipState() {
        showHome()

        composeTestRule.onNodeWithText("⊞ BP").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("⊟ BP").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("⊞ BP").assertIsDisplayed()
    }

    @Test
    fun search_byFormula_findsTopicAndHidesOthers() {
        showHome()
        val formula = ohmsLaw.lesson.sections.firstNotNullOf { it.formula }

        composeTestRule.onNode(hasSetTextAction()).performTextInput(formula)

        composeTestRule.onNodeWithText(ohmsLaw.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(mosfets.title).assertDoesNotExist()
    }

    @Test
    fun search_withNoMatches_showsEmptyState() {
        showHome()

        composeTestRule.onNode(hasSetTextAction()).performTextInput("zzz-no-such-quest")

        composeTestRule.onNodeWithText("No quests found").assertIsDisplayed()
    }

    @Test
    fun lockedCards_announceLockedState_andIgnoreTaps() {
        val clicked = mutableListOf<String>()
        showHome(onTopicClick = { clicked += it })
        val second = TopicsService.allTopics[1]

        val locked = composeTestRule.onAllNodes(
            SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Locked")
        ).fetchSemanticsNodes()
        assertTrue(locked.isNotEmpty(), "expected locked cards to expose a Locked state")

        composeTestRule.onAllNodesWithText(second.title)[0].performClick()
        composeTestRule.onNodeWithText(ohmsLaw.title).performClick()

        composeTestRule.runOnIdle { assertEquals(listOf(ohmsLaw.id), clicked) }
    }

    @Test
    fun reviewChip_hiddenWithNoMistakes() {
        showHome()

        composeTestRule.onNodeWithText("⟲", substring = true).assertDoesNotExist()
    }

    @Test
    fun reviewChip_showsCountAndOpensReview() {
        runBlocking {
            repository.recordAnswer("ohms_law", "a", correct = false)
            repository.recordAnswer("mosfets", "b", correct = false)
        }
        var opened = false
        showHome(onReview = { opened = true })

        composeTestRule.waitUntil(5_000) {
            composeTestRule.onAllNodesWithText("⟲ 2").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("⟲ 2").performClick()

        composeTestRule.runOnIdle { assertTrue(opened) }
    }
}
