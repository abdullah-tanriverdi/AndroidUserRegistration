package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.R
import com.tanriverdi.card2qr.auth.AuthViewModel



// Poppins font ailesi tanımlamaları
val poppinsMedium = FontFamily(Font(R.font.poppins_medium))
val poppinsBold = FontFamily(Font(R.font.poppins_bold))
val poppinsSemibold = FontFamily(Font(R.font.poppins_semibold))


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    // Kullanıcıdan alınacak e-posta ve şifreyi tutan state değişkenleri
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Authentication durumunu gözlemlemek için LiveData kullanımı
    val authState = authViewModel.authState.observeAsState()

    // Mevcut Android bağlamını (context) alır.
    val context = LocalContext.current

    // Bilgi diyalog kutusunun gösterilmesi için state değişkeni
    var showInfoDialog by remember { mutableStateOf(false) }



    // AuthState değeri değiştiğinde çalışacak efekt
    LaunchedEffect(authState.value) {
        when (authState.value) {
            // Kullanıcı başarılı şekilde giriş yaptıysa, home ekranına geçiş yap
            is AuthViewModel.AuthState.Authenicated -> navController.navigate("home"){
                popUpTo("login") { inclusive = true }
            }
            else ->{
                // Diğer durumlar için herhangi bir işlem yapılmaz, ya da başka işlemler eklenebilir
                Unit
            }
        }
    }


    // Scaffold, uygulamanın üst çubuk (top bar) ve içeriğini tanımlar.
    Scaffold (
        topBar = {
            TopAppBar(
                title = { TopBarTitleLogin() },
                actions = { InfoIconButton(onClick = {showInfoDialog=true}) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,

                )
            )
        }
    ){

        // Eğer bilgi diyaloğu açık ise gösterilir.
        if (showInfoDialog){
            InfoDialog(showDialog = { showInfoDialog = false })
        }


        // LazyColumn, giriş ekranının düzenini oluşturur. Elemanlar dikey olarak sıralanır.
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Başlık
            item {

                LoginTitle()
                Spacer(modifier = Modifier.height(32.dp))
            }

            // E-posta giriş alanı
            item {
                EmailInputFieldLogin(email, onValueChange = { email = it })
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Şifre giriş alanı
            item {
                PasswordInputFieldLogin(password, onValueChange = { password = it })
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Giriş yap butonu
            item {
                LoginButton(authState, email, password, authViewModel)
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Kayıt ol navigasyonu
            item {
                SignUpButton(navController)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Şifre sıfırlama navigasyonu
            item {
                ForgotPasswordButton(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }


  }






// Üst çubuğun başlığı
@Composable
fun TopBarTitleLogin(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.QrCode,
            contentDescription = "QR Kod",
            tint = MaterialTheme.colorScheme.primary

        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Card2QR",
            style = TextStyle(
                fontFamily = poppinsSemibold,
                fontSize = 20.sp
            )
        )
    }
}


// Üst çubuktaki bilgi simgesi.
@Composable
fun InfoIconButton(onClick: () -> Unit){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Bilgi",
            tint = MaterialTheme.colorScheme.primary

        )
    }
}

// Bilgi diyaloğu.
@Composable
fun InfoDialog(showDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = showDialog,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Bilgi İkonu",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Bilgi", fontFamily = poppinsSemibold, color = MaterialTheme.colorScheme.onBackground)
            }
        },
        text = {
            Text("Card2QR", fontFamily = poppinsMedium, color = MaterialTheme.colorScheme.onBackground)
        },
        confirmButton = {
            TextButton(onClick = showDialog) {
                Text("Tamam", fontFamily = poppinsMedium, color = MaterialTheme.colorScheme.primary)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}

//Login Başlığı
@Composable
fun LoginTitle(){
    Text(
        text = "Giriş Yap",
    fontFamily = poppinsBold,
    fontSize = 36.sp,
    color = MaterialTheme.colorScheme.onBackground
    )
}

//Email Girişi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputFieldLogin(email: String, onValueChange: (String) -> Unit) {
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

//Parola Girişi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputFieldLogin(password: String, onValueChange: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Parola", style = TextStyle(fontFamily = poppinsMedium))},
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp)))
            .background(Color.Transparent),
        singleLine = true,
        shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)),
        textStyle = TextStyle(fontFamily = poppinsMedium),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Kilit",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisibility) "Şifreyi gizle" else "Şifreyi göster",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedLabelColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary
        )
    )

}

//Giriş Butonu
@Composable
fun LoginButton(authState: State<AuthViewModel.AuthState?>, email: String, password: String, authViewModel: AuthViewModel) {
    val context = LocalContext.current // Compose'un context sağlayıcısı
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
                    authViewModel.login(email, password)
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
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onBackground)
        } else {
            Text(
                text = "Giriş Yap",
                fontSize = 20.sp,
                fontFamily = poppinsSemibold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

//Kayıt Ol Butonu
@Composable
fun SignUpButton(navController: NavController) {
    TextButton(onClick = { navController.navigate("signup") }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Hesabın Yok Mu? Hemen Kayıt Ol!",
            fontSize = 14.sp,
            fontFamily = poppinsMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

//Şifremi Unuttum Butonu
@Composable
fun ForgotPasswordButton(navController: NavController) {
    TextButton(onClick = { navController.navigate("resetPassword") }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Şifremi Unuttum",
            fontSize = 14.sp,
            fontFamily = poppinsMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// Email doğrulama için bir fonksiyon
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// Hata mesajı göstermek için Toast
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

