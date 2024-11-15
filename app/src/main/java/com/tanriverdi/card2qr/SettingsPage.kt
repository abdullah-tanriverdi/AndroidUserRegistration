package com.tanriverdi.card2qr.ui

import android.app.Activity
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.MainScreenContent
import com.tanriverdi.card2qr.loginscreen.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(modifier: Modifier= Modifier,navController: NavController, authViewModel: AuthViewModel) {

    MainScreenContent(
        navController = navController,
        authViewModel = authViewModel,

    )


    // Ana içeriği ve metni bir arada gösterebilirsiniz.
    Column(modifier = modifier.fillMaxSize()) {
        // Başlık metni
        Text(
            text = "Settings Page",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

    }


}

