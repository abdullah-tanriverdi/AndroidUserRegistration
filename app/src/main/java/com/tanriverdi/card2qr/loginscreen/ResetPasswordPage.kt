package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.tanriverdi.card2qr.auth.AuthViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }  // E-posta durumunu tutar
    // Authentication durumunu gözlemlemek için LiveData kullanımı
    val authState = authViewModel.authState.observeAsState()


    // Scaffold kullanarak sayfa düzeni
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarTitleResetPassword() },

                navigationIcon = { NavigateBackIconReset(navController) },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // Arka plan rengi
                    titleContentColor = MaterialTheme.colorScheme.onBackground, // Başlık metni rengi
                )

            )
        }
    ){
        LazyColumn (
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ResetPasswordTitle()
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                EmailInputFieldResetPassword(email, onValueChange = { email = it })
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ResetPasswordButton(email, authViewModel, authState, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }

}

@Composable
fun TopBarTitleResetPassword() {
    Text(
        text = "Card2QR",
        style = TextStyle(fontFamily = poppinsSemibold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
fun NavigateBackIconReset(navController: NavController) {
    IconButton(onClick = {
        navController.navigate("login"){
            popUpTo("resetPassword"){ inclusive = true }
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
fun ResetPasswordTitle(){
    Text(
        text = "Şifre Sıfırlama",
        fontFamily = poppinsBold,
        fontSize = 36.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputFieldResetPassword(email: String, onValueChange: (String) -> Unit) {
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

@Composable
fun ResetPasswordButton(email: String, authViewModel: AuthViewModel, authState: State<AuthViewModel.AuthState?>, navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Button(
        onClick = {

            when {
                email.isBlank() -> {
                    showToast(context, "Lütfen tüm alanları doldurun.")
                }
                !isValidEmail(email) -> {
                    showToast(context, "Lütfen geçerli bir e-posta adresi girin.")
                }
                else -> {
                    authState.value==AuthViewModel.AuthState.Loading// Buton üzerine indicator ekleyebilmek için loading durumunu aktif et
                    authViewModel.resetPassword(email)
                    showToast(context, "E-postanızı gelen bağlantıyı takip edin.")

                    scope.launch {
                        delay(1000)
                        navController.navigate("login") {
                            popUpTo("resetPassword") { inclusive = true } // Kayıt ekranını yığından kaldır
                        }
                    }

                }
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        enabled = authState.value != AuthViewModel.AuthState.Loading
    ) {
        if (authState.value == AuthViewModel.AuthState.Loading) {
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

}


