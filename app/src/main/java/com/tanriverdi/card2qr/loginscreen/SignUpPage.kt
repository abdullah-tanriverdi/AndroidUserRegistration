package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanriverdi.card2qr.auth.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){



// Kullanıcıdan e-posta ve şifre bilgisini almak için durum değişkenleri
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

// Authentication (kimlik doğrulama) durumu LiveData ile gözlemlenir
    val authState = authViewModel.authState.observeAsState()




    // AuthState değeri değiştiğinde çalışacak efekt
    LaunchedEffect(authState.value) {
        when(authState.value){
            // Kullanıcı başarıyla kimlik doğrulandıysa "loginhome" ekranına yönlendirilir
            is AuthViewModel.AuthState.Authenicated -> navController.navigate("loginhome"){
                popUpTo("signup") { inclusive = true }
            }
            else ->{
                // Diğer durumlar için herhangi bir işlem yapılmaz, ya da başka işlemler eklenebilir
                Unit
            }
        }
    }



    // Scaffold kullanarak sayfa düzeni
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarTitleSignUp() },

                navigationIcon = { NavigateBackIcon(navController) },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // Arka plan rengi
                    titleContentColor = MaterialTheme.colorScheme.onBackground, // Başlık metni rengi
                )

            )
        }
    ) {

        LazyColumn (
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            item {
                SignUpTitle()
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
             EmailInputFieldSignUp(email, onValueChange = { email = it })
                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                PasswordInputFieldSignUp(password, onValueChange = { password = it })
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                SignUpButtonSignUp(email, password, authViewModel, authState, navController)
                Spacer(modifier = Modifier.height(32.dp))

            }

            item {
                LoginButtonSignUp(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        }
    }



@Composable
fun TopBarTitleSignUp() {
    Text(
        text = "Card2QR",
        style = TextStyle(fontFamily = poppinsSemibold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
    )

}

@Composable
fun NavigateBackIcon(navController: NavController) {

    IconButton(onClick = {
        navController.navigate("login"){
            popUpTo("signup"){ inclusive = true }
        }
    }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Geri",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SignUpTitle(){
    Text(
        text = "Kayıt Ol",
        fontFamily = poppinsBold,
        fontSize = 36.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputFieldSignUp(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("E-Posta", style = TextStyle(fontFamily = poppinsMedium))},
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
                tint = MaterialTheme.colorScheme.primary

            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında pembe renk
            unfocusedBorderColor =MaterialTheme.colorScheme.primary, // Normalde mavi kenarlık
            cursorColor = MaterialTheme.colorScheme.surfaceContainer, // İmleç rengi
            focusedLabelColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında etiket rengi
            unfocusedLabelColor = MaterialTheme.colorScheme.primary // Normalde gri etiket rengi
        )

    )}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun PasswordInputFieldSignUp(password: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange, // Değeri dışarıdan alınan fonksiyonla günceller
        label = {
            Text(
                text = "Parola",
                style = TextStyle(fontFamily = poppinsMedium)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp)))
            .background(Color.Transparent), // Arka plan rengini şeffaf bırakır
        singleLine = true, // Tek satır metin girişi
        shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)), // Yuvarlak köşe şekli
        textStyle = TextStyle(fontFamily = poppinsMedium), // Yazı stilini ayarlar
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında kenarlık rengi
            unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Normal kenarlık rengi
            cursorColor = MaterialTheme.colorScheme.surfaceContainer, // İmleç rengi
            focusedLabelColor = MaterialTheme.colorScheme.surfaceContainer, // Odaklandığında etiket rengi
            unfocusedLabelColor = MaterialTheme.colorScheme.primary // Normal etiket rengi
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock, // Kilit ikonu
                contentDescription = "Parola İkonu",
                tint = MaterialTheme.colorScheme.primary // İkon rengi
            )
        }
    )
}


@Composable
fun SignUpButtonSignUp(email: String, password: String, authViewModel: AuthViewModel, authState: State<AuthViewModel.AuthState?>, navController: NavController) {

    val context = LocalContext.current
   val scope = rememberCoroutineScope()

    Button(
        onClick = {

            when {
                email.isBlank() || password.isBlank() -> {
                    showToast(context, "Lütfen tüm alanları doldurun.")
                }
                !isValidEmail(email) -> {
                    showToast(context, "Lütfen geçerli bir e-posta adresi girin.")
                }
                else -> {
                    authViewModel.signup(email, password)
                    showToast(context, "E-postanızı doğrulayın.")

                    scope.launch {
                        delay(1000)
                        navController.navigate("login") {
                            popUpTo("signup") { inclusive = true } // Kayıt ekranını yığından kaldır
                        }
                    }

                }
            }

        },
        enabled = authState.value != AuthViewModel.AuthState.Loading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        if (authState.value == AuthViewModel.AuthState.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = "Hesap Oluştur",
                fontSize = 20.sp,
                fontFamily = poppinsSemibold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }



    }

}

@Composable
fun LoginButtonSignUp(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Zaten Hesabın Var Mı? Giriş Yap!",
            fontSize = 14.sp,
            fontFamily = poppinsMedium,
            color =  MaterialTheme.colorScheme.onSurface)

    }
}


