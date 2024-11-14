package com.tanriverdi.card2qr

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tanriverdi.card2qr.loginscreen.AuthViewModel
import com.tanriverdi.card2qr.loginscreen.LoginNavigator
import com.tanriverdi.card2qr.loginscreen.LoginPage
import com.tanriverdi.card2qr.onboardingscreen.OnboardingScreen
import com.tanriverdi.card2qr.onboardingscreen.OnboardingUtils
import com.tanriverdi.card2qr.ui.theme.Card2QRTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {

    // OnboardingUtils örneğini lazy yükleme ile başlat
    private val onboardingUtils by lazy { OnboardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Kenarlardan kenara görüntüleme modunu etkinleştir
        val authViewModel : AuthViewModel by viewModels()
        installSplashScreen() // Splash ekranını başlatır
        Handler(Looper.getMainLooper()).postDelayed({
            setContent {
                Card2QRTheme {  // Temayı ayarlayın
                    Surface(color = MaterialTheme.colorScheme.background) {
                        // Onboarding tamamlandıysa ana ekranı göster, aksi takdirde onboarding ekranını göster
                        if (onboardingUtils.isOnboardingCompleted()) {
                            // Scaffold içindeki contentPadding ile innerPadding tanımlanır
                            Scaffold { innerPadding ->
                                LoginNavigator(
                                    modifier = Modifier.padding(innerPadding),
                                    authViewModel = authViewModel
                                )
                            }
                        } else {
                            ShowOnboardingScreen()
                        }
                    }
                }
            }
        }, 1) // 2000 ms (2 saniye) gecikme ile splash ekranını geçiş yapacak şekilde ayarlayın.
    }




    // Onboarding ekranını gösteren bileşen
@Composable
private fun ShowOnboardingScreen() {
    val scope = rememberCoroutineScope() // Coroutine kapsamını hatırla
        val authViewModel : AuthViewModel by viewModels()

        OnboardingScreen {
        onboardingUtils.setOnboardingCompleted() // Onboarding tamamlandığında durumu güncelle
        scope.launch {
            setContent {
                LoginNavigator(modifier = Modifier,authViewModel = authViewModel)
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Card2QRTheme {

    }

}
}