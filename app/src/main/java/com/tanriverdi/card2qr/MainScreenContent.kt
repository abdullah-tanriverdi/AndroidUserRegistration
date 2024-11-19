package com.tanriverdi.card2qr

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import com.tanriverdi.card2qr.auth.AuthViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(navController: NavController, authViewModel: AuthViewModel) {


    val items = listOf(
        R.drawable.baseline_settings_24,
        R.drawable.baseline_home_filled_24,
        R.drawable.baseline_favorite_24,
        R.drawable.baseline_person_24,
    )

    val labels = listOf(
        "Ayarlar",
        "Ana Sayfa",
        "Favoriler",
        "Profil",
    )

    // selectedIndex'in doğru başlangıç değeri ile başlaması için güncel navController state'ini kullanıyoruz
    val selectedIndex = rememberSaveable { mutableIntStateOf(1) } // Home başlangıçta seçili

    // NavController'ın back stack'ini takip etmek için bir observer kullanıyoruz
    navController.currentBackStackEntry?.let { backStackEntry ->
        // BackStack'den hedef sayfanın ismini alıyoruz ve selectedIndex'i ona göre ayarlıyoruz
        selectedIndex.value = when (backStackEntry.destination.route) {
            "settings" -> 0
            "home" -> 1
            "favorites" -> 2
            "profile" -> 3
            else -> 1 // default olarak home
        }
    }

    val buttons = items.mapIndexed { index, iconData ->
        RioBottomNavItemData(
            imageVector = ImageVector.vectorResource(iconData),
            selected = index == selectedIndex.intValue,
            onClick = {
                selectedIndex.intValue = index
                when (index) {
                    0 -> navController.navigate("settings") {
                        // Geçerli sayfadan önceki sayfayı temizle
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "home") { inclusive = true }

                    }
                    1 -> navController.navigate("home") {
                        // Geçerli sayfadan önceki sayfayı temizle
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "home") { inclusive = true }
                    }
                    2 -> navController.navigate("favorites") {
                        // Geçerli sayfadan önceki sayfayı temizle
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "home") { inclusive = true }
                    }
                    3 -> navController.navigate("profile") {
                        // Geçerli sayfadan önceki sayfayı temizle
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "home") { inclusive = true }
                    }
                }
            },
            label = labels[index]
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

        bottomBar = {
            RioBottomNavigation(
                fabIcon = Icons.Default.Add,
                buttons = buttons,
                onFabClick = { /* QR kod işlemleri buraya eklenebilir */ },
                fabSize = 70.dp,
                barHeight = 70.dp,
                selectedItemColor = colorResource(id = R.color.green),
                fabBackgroundColor = colorResource(id = R.color.green),
            )
        },
        content = { }
    )
}
