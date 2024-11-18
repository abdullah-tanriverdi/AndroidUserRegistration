package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Geri gitme ikonu
                            contentDescription = "Geri",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // Arka plan rengi
                    titleContentColor = MaterialTheme.colorScheme.onBackground, // Başlık metni rengi
                )
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Şifre Sıfırla",
                fontSize = 36.sp,
                fontFamily = poppinsBold,
                color = MaterialTheme.colorScheme.onBackground
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
                    focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında pembe renk
                    unfocusedBorderColor =MaterialTheme.colorScheme.primary, // Normalde mavi kenarlık
                    cursorColor = MaterialTheme.colorScheme.surfaceContainer, // İmleç rengi
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında etiket rengi
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary // Normalde gri etiket rengi
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
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Bağlantı Gönder",
                        fontSize = 16.sp,
                        fontFamily = poppinsSemibold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (authState) {

                is AuthState.Success -> {
                    LaunchedEffect(Unit) {
                        // Şifre sıfırlama başarılıysa, login sayfasına yönlendir
                        if (navController.currentDestination?.route != "login") {
                            navController.navigate("login") {
                                // Mevcut sayfayı temizle
                                popUpTo(0) { inclusive = false }
                            }
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
