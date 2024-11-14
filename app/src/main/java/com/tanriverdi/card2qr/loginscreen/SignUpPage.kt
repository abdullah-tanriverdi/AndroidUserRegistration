package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.navOptions
import com.tanriverdi.card2qr.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){

    // Kullanıcı bilgilerini saklamak için iki durum değişkeni oluşturuluyor
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Authentication durumu gözlemleniyor
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenicated -> navController.navigate("loginhome")
            is AuthState.Error-> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else ->Unit
        }
    }


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
                .fillMaxWidth()
                .background(colorResource(id= R.color.white))
                .padding(32.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Hesap Oluştur",
                fontSize = 36.sp, // Biraz daha büyük yazı boyutu
                fontFamily = poppinsBold, // Bold font
                color = colorResource(id = R.color.green), // Tek renk kullanımı

            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-Posta",
                    style = TextStyle(fontFamily = poppinsMedium) ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large.copy(
                        CornerSize(24.dp)
                    ))
                    .background(Color.Transparent),
                singleLine = true,
                shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)),
                textStyle = TextStyle(fontFamily = poppinsMedium),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "E-Posta Ikonu",
                        tint = colorResource(id = R.color.gray) // İkon rengi
                    )
                },

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.pink), // Odaklandığında pembe renk
                    unfocusedBorderColor = colorResource(id = R.color.blue), // Normalde mavi kenarlık
                    cursorColor = colorResource(id = R.color.green), // İmleç rengi
                    focusedLabelColor = colorResource(id = R.color.pink), // Odaklandığında etiket rengi
                    unfocusedLabelColor = colorResource(id = R.color.blue) // Normalde gri etiket rengi
                )
            )

            Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Parola",
                    style = TextStyle(fontFamily = poppinsMedium) )},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp))) // Yuvarlak köşeler
                    .background(Color.Transparent),

                singleLine = true,
                shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)),
                textStyle = TextStyle(fontFamily = poppinsMedium),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.pink), // Odaklandığında pembe renk
                    unfocusedBorderColor = colorResource(id = R.color.blue), // Normalde mavi kenarlık
                    cursorColor = colorResource(id = R.color.green), // İmleç rengi
                    focusedLabelColor = colorResource(id = R.color.pink), // Odaklandığında etiket rengi
                    unfocusedLabelColor = colorResource(id = R.color.blue) // Normalde gri etiket rengi
                ),

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Filled.Lock, // Kilit ikonu
                        contentDescription = "Kilit",
                        tint = colorResource(id = R.color.gray)
                    )
                }

            )

            Spacer(modifier = Modifier.height(16.dp))

            var isLoading by remember { mutableStateOf(false) }

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            // Kayıt işlemi başlatılır
                            authViewModel.signup(email, password)

                            // Yüklenme göstergesi başlatılır
                            isLoading = true
                        } else {
                            Toast.makeText(context, "Geçerli Bir E-Posta Adresi Girin!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "E-posta ve şifre boş olamaz", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = authState.value != AuthState.Loading && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Hesap Oluştur",
                        fontSize = 20.sp,
                        fontFamily = poppinsSemibold,
                        color = Color.White
                    )
                }
            }

// Yüklenme durumu değiştiğinde çalışır
            LaunchedEffect(isLoading) {
                if (isLoading) {
                    kotlinx.coroutines.delay(2000) // 2 saniye bekletme
                    isLoading = false

                    // Login ekranına yönlendirme
                    navController.navigate("login", navOptions {
                        popUpTo("signup") { inclusive = true }
                        launchSingleTop = true
                    })
                }
            }



            Spacer(modifier = Modifier.height(16.dp))


            TextButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Zaten Hesabın Var Mı? Giriş Yap!",
                    fontSize = 14.sp,
                    fontFamily = poppinsMedium,
                    color = colorResource(id = R.color.gray)
                )
            }



        }
    }



}

