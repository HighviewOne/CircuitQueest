package com.circuitqueest.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.circuitqueest.app.CircuitQueestApp
import com.circuitqueest.app.data.repository.ProgressRepository
import com.circuitqueest.app.ui.screens.HomeScreen
import com.circuitqueest.app.ui.screens.LessonScreen
import com.circuitqueest.app.ui.screens.QuizScreen
import com.circuitqueest.app.ui.screens.ResultScreen
import com.circuitqueest.app.viewmodel.HomeViewModel
import com.circuitqueest.app.viewmodel.LessonViewModel
import com.circuitqueest.app.viewmodel.QuizViewModel

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
fun CircuitQueestNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as CircuitQueestApp
    val repository = ProgressRepository(
        progressDao = app.database.progressDao(),
        quizResultDao = app.database.quizResultDao()
    )

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.Factory(repository)
            )
            HomeScreen(
                viewModel = homeViewModel,
                onTopicClick = { topicId ->
                    navController.navigate(Routes.lesson(topicId))
                }
            )
        }

        composable(
            route = Routes.LESSON,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: return@composable
            val lessonViewModel: LessonViewModel = viewModel(
                factory = LessonViewModel.Factory(topicId, repository)
            )
            LessonScreen(
                viewModel = lessonViewModel,
                onBack = { navController.popBackStack() },
                onStartQuiz = { id ->
                    navController.navigate(Routes.quiz(id))
                }
            )
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: return@composable
            val quizViewModel: QuizViewModel = viewModel(
                factory = QuizViewModel.Factory(topicId, repository)
            )
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
                }
            )
        }
    }
}
