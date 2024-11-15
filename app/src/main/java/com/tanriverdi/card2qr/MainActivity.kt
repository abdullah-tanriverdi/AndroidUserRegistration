package com.tanriverdi.card2qr

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tanriverdi.card2qr.loginscreen.AuthViewModel

import com.tanriverdi.card2qr.loginscreen.LoginPage
import com.tanriverdi.card2qr.onboardingscreen.OnboardingScreen
import com.tanriverdi.card2qr.onboardingscreen.OnboardingUtils
import com.tanriverdi.card2qr.ui.theme.Card2QRTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tanriverdi.card2qr.loginscreen.AppNavigaton


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

                // Durum çubuğu ve navigasyon çubuğunu her zaman görünür tutma ve koyu renk yapma
                val context = LocalContext.current
                val activity = context as? Activity

                DisposableEffect(context) {
                    // Durum çubuğu ve navigasyon çubuğunu koyu renkte ayarlama
                    activity?.window?.decorView?.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_VISIBLE or
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or  // Koyu renkli durum çubuğu
                                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR  // Koyu renkli navigasyon çubuğu

                    // Navigasyon çubuğunun arka planını koyu yapmak için
                    activity?.window?.navigationBarColor = Color.Black.toArgb()  // Navigasyon çubuğu rengini siyah yapıyoruz

                    onDispose {
                        // Uygulama kapanırken varsayılan görünümde kalır
                        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
                Card2QRTheme {  // Temayı ayarlayın
                    Surface(color = MaterialTheme.colorScheme.background) {
                        // Onboarding tamamlandıysa ana ekranı göster, aksi takdirde onboarding ekranını göster
                        if (onboardingUtils.isOnboardingCompleted()) {
                            // Scaffold içindeki contentPadding ile innerPadding tanımlanır
                            Scaffold { innerPadding ->
                                AppNavigaton(
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
                AppNavigaton(modifier = Modifier,authViewModel = authViewModel)
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