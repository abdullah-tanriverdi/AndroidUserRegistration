package com.tanriverdi.card2qr.loginscreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tanriverdi.card2qr.R


val poppinsMedium = FontFamily(Font(R.font.poppins_medium))
val poppinsBold = FontFamily(Font(R.font.poppins_bold))
val poppinsSemibold = FontFamily(Font(R.font.poppins_semibold))

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    var showInfoDialog by remember { mutableStateOf(false) }



    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenicated -> navController.navigate("home"){
                popUpTo("login") { inclusive = true }
            }
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

  Scaffold (

      topBar = {

      TopAppBar(

          title = { Text("Card2QR", style = TextStyle(fontFamily = poppinsSemibold, fontSize = 20.sp)) },
          navigationIcon = {

                  Icon(
                      imageVector = Icons.Default.QrCode,  // QR kod simgesi
                      contentDescription = "QR Kod",
                      tint = Color.Black
                  )

          },


          actions = {
              IconButton(onClick = { showInfoDialog = true }) {
                  Icon(
                      imageVector = Icons.Default.Info, // Bilgi ikonu
                      contentDescription = "Bilgi",
                      tint = colorResource(id = R.color.gray) // İkon rengi
                  )
              }
          },


      )
  }){
      // Dialog gösterme koşulu
      if (showInfoDialog) {
          AlertDialog(
              onDismissRequest = { showInfoDialog = false },
              title = {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                      Icon(
                          imageVector = Icons.Default.Info,
                          contentDescription = "Bilgi İkonu",
                          tint = colorResource(id = R.color.gray),
                          modifier = Modifier.padding(end = 8.dp)
                      )
                      Text("Bilgi" , fontFamily = poppinsSemibold)
                  }
              },
              text = { Text("Card2QR" , fontFamily = poppinsMedium) },
              confirmButton = {
                  TextButton(onClick = { showInfoDialog = false }) {
                      Text("Tamam", fontFamily = poppinsMedium, color = colorResource(id = R.color.green))
                  }
              }
          )
      }

      Column(
          modifier = modifier
              .fillMaxSize()
              .fillMaxWidth()
              .background(colorResource(id= R.color.white))
              .padding(32.dp),

          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          Text(
              text = "Giriş Yap",
              fontSize = 36.sp, // Biraz daha büyük yazı boyutu
              fontFamily = poppinsBold, // Bold font
              color = colorResource(id = R.color.green), // Tek renk kullanımı

          )


          Spacer(modifier = Modifier.height(24.dp))

          OutlinedTextField(
              value = email,
              onValueChange = { email = it },
              label = { Text("E-Posta",
                  style = TextStyle(fontFamily = poppinsMedium) )},
              modifier = Modifier
                  .fillMaxWidth()
                  .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp))) // Yuvarlak köşeler
                  .background(Color.Transparent), // Şeffaf arka plan
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


          var passwordVisibility by remember { mutableStateOf(false) } // Şifre görünürlüğü


          OutlinedTextField(
              value = password,

              onValueChange = { password = it },
              label = { Text("Parola",
                  style = TextStyle(fontFamily = poppinsMedium) )},
              modifier = Modifier
                  .fillMaxWidth()
                  .clip(MaterialTheme.shapes.large.copy(CornerSize(24.dp))) // Yuvarlak köşeler
                  .background(Color.Transparent), // Şeffaf arka plan,
              singleLine = true,
              shape = MaterialTheme.shapes.large.copy(CornerSize(24.dp)),
              textStyle = TextStyle(fontFamily = poppinsMedium),
              visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(), // Şifreyi gösterme/gizleme


              colors = TextFieldDefaults.outlinedTextFieldColors(
                  focusedBorderColor = colorResource(id = R.color.pink), // Odaklandığında pembe renk
                  unfocusedBorderColor = colorResource(id = R.color.blue), // Normalde mavi kenarlık
                  cursorColor = colorResource(id = R.color.green), // İmleç rengi
                  focusedLabelColor = colorResource(id = R.color.pink), // Odaklandığında etiket rengi
                  unfocusedLabelColor = colorResource(id = R.color.blue) // Normalde gri etiket rengi
              ),
              trailingIcon = {
                  // Göz simgesi, şifreyi gösterme/gizleme
                  IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                      Icon(
                          imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                          contentDescription = if (passwordVisibility) "Şifreyi gizle" else "Şifreyi göster",
                          tint = colorResource(id = R.color.gray) // İkon rengi
                      )
                  }
              },

              leadingIcon = {
                  // Göz simgesi çizik
                  Icon(
                      imageVector = Icons.Filled.Lock, // Kilit ikonu
                      contentDescription = "Kilit",
                      tint = colorResource(id = R.color.gray)
                  )
              }
          )

          Spacer(modifier = Modifier.height(24.dp))

          Button(
              onClick = {
                  authViewModel.login(email, password) // Giriş işlemini başlat
              },
              enabled = authState.value != AuthState.Loading, // Yalnızca yükleme durumu olmadığı zaman buton aktif
              modifier = Modifier
                  .fillMaxWidth()
                  .height(56.dp), // Buton yüksekliği
              shape = MaterialTheme.shapes.medium.copy(CornerSize(12.dp)), // Butonun köşe yuvarlatması
              colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)) // Buton rengi
          ) {
              // Eğer yükleme durumu varsa, yükleniyor göstergesi göster
              if (authState.value == AuthState.Loading) {
                  CircularProgressIndicator(
                      modifier = Modifier.size(24.dp), // İndikatör boyutu
                      color = colorResource(id= R.color.green) // İndikatör rengi
                  )
              } else {
                  Text(
                      text = "Giriş Yap", // Buton metni
                      fontSize = 20.sp, // Yazı boyutu
                      fontFamily = poppinsSemibold, // Font ailesi
                      color = Color.White // Yazı rengi
                  )
              }
          }




          Spacer(modifier = Modifier.height(16.dp))

          TextButton(
              onClick = {
                  navController.navigate("signup")
              },
              modifier = Modifier.fillMaxWidth(),
          ) {
              Text(
                  text = "Hesabın Yok Mu? Hemen Kayıt Ol!",
                  fontSize = 14.sp,
                  fontFamily = poppinsMedium,
                  color = colorResource(id=R.color.gray)
              )
          }



          Spacer(modifier = Modifier.height(1.dp))

          TextButton(onClick = {
              navController.navigate("resetPassword")

          },
              modifier = Modifier.fillMaxWidth()
          ) {
              Text(
                  text = "Şifremi Unuttum",
                  fontFamily = poppinsMedium,
                  fontSize = 14.sp,
                  color = colorResource(id= R.color.gray)
              )
          }
      }

  }


}
