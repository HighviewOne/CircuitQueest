@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.circuitqueest.app.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.circuitqueest.app.ui.screens.HomeScreen
import com.circuitqueest.app.ui.screens.LessonScreen
import com.circuitqueest.app.ui.screens.QuizScreen
import com.circuitqueest.app.ui.screens.ResultScreen
import com.circuitqueest.app.viewmodel.HomeViewModel
import com.circuitqueest.app.viewmodel.LessonViewModel
import com.circuitqueest.app.viewmodel.QuizViewModel

val LocalNavSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

object Routes {
    const val HOME = "home"
    const val LESSON = "lesson/{topicId}"
    const val QUIZ = "quiz/{topicId}"
    const val RESULT = "result/{topicId}/{score}/{total}"

    fun lesson(topicId: String) = "lesson/$topicId"
    fun quiz(topicId: String) = "quiz/$topicId"
    fun result(topicId: String, score: Int, total: Int) = "result/$topicId/$score/$total"
}

@Composable
fun CircuitQueestNavGraph(
    onToggleBlueprint: () -> Unit = {},
    blueprintMode: Boolean = false
) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Routes.HOME) {

            composable(Routes.HOME) {
                CompositionLocalProvider(
                    LocalNavSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalNavAnimatedVisibilityScope provides this
                ) {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        viewModel = homeViewModel,
                        onTopicClick = { topicId ->
                            navController.navigate(Routes.lesson(topicId))
                        },
                        onToggleBlueprint = onToggleBlueprint,
                        blueprintMode = blueprintMode
                    )
                }
            }

            composable(
                route = Routes.LESSON,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) {
                CompositionLocalProvider(
                    LocalNavSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalNavAnimatedVisibilityScope provides this
                ) {
                    val lessonViewModel: LessonViewModel = hiltViewModel()
                    LessonScreen(
                        viewModel = lessonViewModel,
                        onBack = { navController.popBackStack() },
                        onStartQuiz = { id ->
                            navController.navigate(Routes.quiz(id))
                        }
                    )
                }
            }

            composable(
                route = Routes.QUIZ,
                arguments = listOf(navArgument("topicId") { type = NavType.StringType })
            ) {
                CompositionLocalProvider(
                    LocalNavSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalNavAnimatedVisibilityScope provides this
                ) {
                    val quizViewModel: QuizViewModel = hiltViewModel()
                    QuizScreen(
                        viewModel = quizViewModel,
                        onBack = { navController.popBackStack() },
                        onQuizComplete = { id, score, total ->
                            navController.navigate(Routes.result(id, score, total)) {
                                popUpTo(Routes.HOME)
                            }
                        }
                    )
                }
            }

            composable(
                route = Routes.RESULT,
                arguments = listOf(
                    navArgument("topicId") { type = NavType.StringType },
                    navArgument("score") { type = NavType.IntType },
                    navArgument("total") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getString("topicId") ?: return@composable
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                val total = backStackEntry.arguments?.getInt("total") ?: 0

                CompositionLocalProvider(
                    LocalNavSharedTransitionScope provides this@SharedTransitionLayout,
                    LocalNavAnimatedVisibilityScope provides this
                ) {
                    ResultScreen(
                        topicId = topicId,
                        score = score,
                        totalQuestions = total,
                        onRetry = { id ->
                            navController.navigate(Routes.quiz(id)) {
                                popUpTo(Routes.HOME)
                            }
                        },
                        onHome = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        },
                        onNextLesson = { id ->
                            navController.navigate(Routes.lesson(id)) {
                                popUpTo(Routes.HOME)
                            }
                        }
                    )
                }
            }
        }
    }
}
