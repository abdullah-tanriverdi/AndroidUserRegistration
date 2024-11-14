package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    val authState by authViewModel.authState.observeAsState()
    var isLoading by remember { mutableStateOf(false) }  // Buton üzerindeki indicator kontrolü

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Card2QR",
                        style = TextStyle(fontFamily = poppinsSemibold, fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Geri gitme işlemi, navController kullanılarak yapılabilir
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Geri gitme ikonu
                            contentDescription = "Geri",
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier.background(colorResource(id = R.color.white))
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.white))
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Şifre Sıfırla",
                fontSize = 36.sp,
                fontFamily = poppinsBold,
                color = colorResource(id = R.color.green)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-Posta", style = TextStyle(fontFamily = poppinsMedium)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp)))
                    .background(Color.Transparent),
                singleLine = true,
                shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)),
                textStyle = TextStyle(fontFamily = poppinsMedium),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "E-Posta Ikonu",
                        tint = colorResource(id = R.color.gray)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.pink),
                    unfocusedBorderColor = colorResource(id = R.color.blue),
                    cursorColor = colorResource(id = R.color.green),
                    focusedLabelColor = colorResource(id = R.color.pink),
                    unfocusedLabelColor = colorResource(id = R.color.blue)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = true  // Buton üzerine indicator ekleyebilmek için loading durumunu aktif et
                    authViewModel.resetPassword(email)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                        enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = colorResource(id= R.color.green),
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Bağlantı Gönder",
                        fontSize = 16.sp,
                        fontFamily = poppinsSemibold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (authState) {
                is AuthState.Loading -> {
                    // Burada ek bir indicator olmasın çünkü buton üstündeki indicator kullanılacak
                }
                is AuthState.Success -> {
                    LaunchedEffect(Unit) {
                        // Başarı durumunda, giriş ekranına yönlendirme
                        navController.navigate("login") {
                            popUpTo("resetPassword") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
                is AuthState.Error -> {
                    // Hata durumunda, buton üzerindeki indicator'ü kapat
                    isLoading = false
                }
                else -> {}
            }
        }
    }
}
