package com.tanriverdi.card2qr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tanriverdi.card2qr.auth.AuthViewModel
import com.tanriverdi.card2qr.loginscreen.LoginPage
import com.tanriverdi.card2qr.loginscreen.ResetPasswordPage
import com.tanriverdi.card2qr.loginscreen.SignUpPage
import com.tanriverdi.card2qr.ui.SettingsPage


/* Bu yapı, Jetpack Navigation Component kullanarak uygulama içindeki
ekranlar arasında düzgün ve yönetilebilir bir geçiş yapmayı sağlar.
*/


@Composable
fun AppNavigaton(modifier: Modifier = Modifier, authViewModel: AuthViewModel){

    // Navigation controller, sayfalar arasında geçiş yapmayı sağlar.
    val navController = rememberNavController()


     // NavHost, uygulamanın yönlendirmeleri için kullanılan bir konteynırdır.
    // startDestination, uygulama başlatıldığında hangi sayfada başlanacağını belirtir.
    NavHost(navController = navController, startDestination = "login", builder = {

        composable("login"){
            LoginPage(modifier,navController, authViewModel)
        }

        composable("signup"){
            SignUpPage(modifier,navController, authViewModel)
        }

        composable("home"){
            HomePage(modifier,navController, authViewModel)
        }

        composable("resetPassword") {
            ResetPasswordPage(modifier, navController,authViewModel)
        }

        composable("settings") {
            SettingsPage( modifier,navController, authViewModel )
        }
        composable("profile") {
            ProfilePage(modifier,navController, authViewModel )
        }
        composable("favorites") {
            FavoritePage(modifier,navController, authViewModel )
        }


    })
}
