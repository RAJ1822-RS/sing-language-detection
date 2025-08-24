package com.internship.onboarding.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.internship.onboarding.ui.screens.HomeScreen
import com.internship.onboarding.ui.screens.StepDetailScreen

@Composable
fun OnboardingNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStepClick = { stepId ->
                    navController.navigate("step_detail/$stepId")
                }
            )
        }
        composable("step_detail/{stepId}") { backStackEntry ->
            val stepId = backStackEntry.arguments?.getString("stepId") ?: ""
            StepDetailScreen(
                stepId = stepId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}