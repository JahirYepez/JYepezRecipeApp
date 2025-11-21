package com.pjasoft.recipeapp

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pjasoft.recipeapp.ui.theme.RecipeTheme
import com.pjasoft.recipeapp.ui.Screens.Auth.LoginScreen
import com.pjasoft.recipeapp.ui.Screens.Auth.RegisterScreen
import com.pjasoft.recipeapp.ui.Screens.HomeScreen.HomeScreen
import com.pjasoft.recipeapp.ui.Screens.HomeScreenRoute
import com.pjasoft.recipeapp.ui.Screens.LoginScreenRoute
import com.pjasoft.recipeapp.ui.Screens.RegisterScreenRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    RecipeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginScreenRoute
        ){
            composable <RegisterScreenRoute>{
                RegisterScreen(
                    navController = navController
                )
            }
            composable <LoginScreenRoute>{
                LoginScreen(
                    navController = navController
                )
            }

            composable <HomeScreenRoute>{
                HomeScreen(navController)
            }
        }
    }
}