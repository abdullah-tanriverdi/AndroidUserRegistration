package com.tanriverdi.card2qr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.auth.AuthViewModel
@Composable
fun FavoritePage(modifier: Modifier= Modifier,navController: NavController, authViewModel: AuthViewModel){
    MainScreenContent(navController, authViewModel)


    // Ana içeriği ve metni bir arada gösterebilirsiniz.
    Column(modifier = modifier.fillMaxSize()) {
        // Başlık metni
        Text(
            text = "Favorite Page",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}