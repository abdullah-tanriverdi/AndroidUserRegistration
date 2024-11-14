package com.tanriverdi.card2qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.loginscreen.AuthState
import com.tanriverdi.card2qr.loginscreen.AuthViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Box(
        modifier = modifier.fillMaxSize().background(Color(0xFFF1F1F1))
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with title
            Text(
                text = "Welcome to Card2QR",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Card with user info
            Card(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp)),

            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Placeholder Circle for user avatar
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Gray, CircleShape)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Your Profile",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF333333),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Manage your digital card easily",
                        fontSize = 14.sp,
                        color = Color(0xFF777777),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Sign out button
                    TextButton(
                        onClick = {
                            authViewModel.signout()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF6200EE))
                    ) {
                        Text(text = "Sign Out", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

