package com.tanriverdi.card2qr.loginscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tanriverdi.card2qr.HomePage


@Composable
fun LoginNavigator(modifier: Modifier = Modifier, authViewModel: AuthViewModel){

    // Navigation controller, sayfalar arasında geçiş yapmayı sağlar.
    val navController = rememberNavController()


// NavHost, uygulamanın yönlendirmeleri için kullanılan bir konteynırdır.
    // startDestination, uygulama başlatıldığında hangi sayfada başlanacağını belirtir.
    NavHost(navController = navController, startDestination = "login", builder = {

        // "login" rotası için LoginPage'i çağırır.
        composable("login"){
            LoginPage(modifier,navController, authViewModel)
        }

        // "signup" rotası için SignUpPage'i çağırır.
        composable("signup"){
            SignUpPage(modifier,navController, authViewModel)
        }

        // "home" rotası için HomePage'i çağırır.
        composable("home"){
            HomePage(modifier,navController, authViewModel)
        }

        // "resetPassword" rotası için ResetPasswordPage'i çağırır.
        composable("resetPassword") {
            ResetPasswordPage(modifier, navController,authViewModel)
        }


    })
}