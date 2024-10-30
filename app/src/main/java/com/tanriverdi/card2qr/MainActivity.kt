package com.tanriverdi.card2qr

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tanriverdi.card2qr.onboardingscreen.OnboardingScreen
import com.tanriverdi.card2qr.onboardingscreen.OnboardingUtils
import com.tanriverdi.card2qr.ui.theme.Card2QRTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    // OnboardingUtils örneğini lazy yükleme ile başlat
    private val onboardingUtils by lazy { OnboardingUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Kenarlardan kenara görüntüleme modunu etkinleştir
        installSplashScreen() // Splash ekranını başlatır
        setContent {
            Card2QRTheme {  // Temayı ayarlayın
                Surface (color = MaterialTheme.colorScheme.background){
                    // Onboarding tamamlandıysa ana ekranı göster, aksi takdirde onboarding ekranını göster
                    if (onboardingUtils.isOnboardingCompleted()) {
                        ShowHomeScreen()
                    } else {
                        ShowOnboardingScreen()

                    }
                }
            }

        }
    }



@Composable
private fun ShowHomeScreen() {

    Column {
        Text(text = "Home Screen", style = MaterialTheme.typography.headlineLarge)
    }


}
    // Onboarding ekranını gösteren bileşen
@Composable
private fun ShowOnboardingScreen() {
    val scope = rememberCoroutineScope() // Coroutine kapsamını hatırla
    OnboardingScreen {
        onboardingUtils.setOnboardingCompleted() // Onboarding tamamlandığında durumu güncelle
        scope.launch {
            setContent {
                ShowHomeScreen() // Onboarding tamamlandığında ana ekranı göster
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Card2QRTheme {
        ShowHomeScreen()
    }

}
}