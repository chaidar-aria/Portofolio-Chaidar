package com.chaidar.savedisasterportofolio

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chaidar.savedisasterportofolio.model.clasterizationList
import com.chaidar.savedisasterportofolio.model.evacuationRouteList
import com.chaidar.savedisasterportofolio.preferences.UserPreference
import com.chaidar.savedisasterportofolio.ui.navigation.Actions
import com.chaidar.savedisasterportofolio.ui.navigation.Destination
import com.chaidar.savedisasterportofolio.ui.navigation.Destination.EvacuationRouteDetail
import com.chaidar.savedisasterportofolio.ui.screen.clasterization.ClasterizationScreenDetail
import com.chaidar.savedisasterportofolio.ui.screen.dashboard.DashboardScreen
import com.chaidar.savedisasterportofolio.ui.screen.evacuation.EvacuationRouteDetail
import com.chaidar.savedisasterportofolio.ui.screen.login.LoginScreen
import com.chaidar.savedisasterportofolio.ui.screen.profile.ProfileScreen
import com.chaidar.savedisasterportofolio.ui.screen.settings.SettingsScreen
import com.chaidar.savedisasterportofolio.ui.screen.register.RegisterScreen


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreference = remember {
        UserPreference(context)
    }
    val userEmail = userPreference.getUserEmail()

    val actions = remember(navController) { Actions(navController) }

    if (userEmail != null) {
        DashboardScreen(actions, navController)
    } else {
        LoginScreen(onLoginSuccess = actions.openDashboard, navController)
    }

    MaterialTheme {
        NavHost(navController = navController, startDestination = Destination.Dashboard) {

//            composable(Destination.Register) {
//                RegisterScreen(onRegisterSuccess = actions.openDashboard, navController)
//            }
//
//            composable(Destination.Login) {
//                LoginScreen(onLoginSuccess = actions.openDashboard, navController)
//            }

            composable(Destination.Dashboard) {
                DashboardScreen(actions, navController)
            }

            composable(Destination.Profile) {
                ProfileScreen(actions)
            }

            composable(Destination.Settings) {
                SettingsScreen()
            }

            composable(
                "${Destination.EvacuationRouteDetail}/{routeId}",
                arguments = listOf(navArgument("routeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val routeId = backStackEntry.arguments?.getInt("routeId")
                val routeModel = evacuationRouteList.find { it.textCategoryEvacuationRoute == routeId }
                routeModel?.let {
                    EvacuationRouteDetail(model = it, textCategory = null)
                }
            }

            composable(
                "${Destination.ClasterizationScreenDetail}/{routeId}",
                arguments = listOf(navArgument("routeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val routeId = backStackEntry.arguments?.getInt("routeId")
                val routeModel = clasterizationList.find { it.textCategoryClasterization == routeId }
                routeModel?.let {
                    ClasterizationScreenDetail(model = it, textCategory = null)

                }
            }
        }
    }
}