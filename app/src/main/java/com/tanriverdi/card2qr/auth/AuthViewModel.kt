package com.tanriverdi.card2qr.auth

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/*
* MVMM mimarisine uygun auth viewmodel sınfı
* */

class AuthViewModel(): ViewModel() {

    // FirebaseAuth örneğini AuthService içinde tanımlayacağız.
    private val authService: AuthService = AuthService(FirebaseAuth.getInstance())



    // _authState, kullanıcı kimlik doğrulama durumunu tutar.
    private val _authState = MutableLiveData<AuthState>()


    // authState, dışarıdan yalnızca okuma amaçlı erişilebilen bir LiveData sağlar.
    val authState: LiveData<AuthState> = _authState



    // ViewModel başlatıldığında, kimlik doğrulama durumunu kontrol etmek için checkAuthStatus fonksiyonu çağrılır.
    init {
        checkAuthStatus()
    }

    // Kullanıcının mevcut kimlik doğrulama durumunu kontrol eder.
    fun checkAuthStatus() {
        // Mevcut kullanıcıyı al
        val user = authService.getCurrentUser()
        // Kullanıcı giriş yapmamışsa veya doğrulama yapılmamışsa, durum "Unauthenticated" olarak güncellenir.
        if (user == null) {
            _authState.value = AuthState.Unauthenticated
        } else if (user.isEmailVerified) {
            // Kullanıcı giriş yapmış ve e-posta doğrulanmışsa, durum "Authenticated" olarak güncellenir.
            _authState.value = AuthState.Authenicated
        } else {

        }

    }

    // Kullanıcı girişi için gerekli işlemleri başlatır.
    fun login(email: String, password: String) {

        // Kullanıcı girişi için gerekli işlemleri başlatır.
        _authState.value= AuthState.Loading

        // AuthService'den giriş işlemi çağrılır.
        authService.signInWithEmail(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // İşlem başarılıysa, kullanıcıyı kontrol et
                    val user = authService.getCurrentUser()
                    if (user?.isEmailVerified == true) {
                        // Eğer e-posta doğrulanmışsa, durum "Authenticated" olarak güncellenir.
                        _authState.value = AuthState.Authenicated
                    } else {
                        // E-posta doğrulaması yapılmamışsa
                        _authState.value = AuthState.Error("E-posta doğrulanmamış.")
                    }
                }else{
                    // İşlem başarısızsa başarısız olarak güncellenir.
                    _authState.value = AuthState.Error("Giriş başarısız.")

                }
            }

    }


    // Kullanıcı kaydı oluşturulması için işlemleri başlatır.
    fun signup(email: String, password: String){

        // Kayıt işlemi sırasında "Loading" durumu gösterilir.
        _authState.value = AuthState.Loading

        // AuthService'den yeni kullanıcı kaydı işlemi çağrılır.
        authService.createUserWithEmail(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    // Kayıt başarılıysa, e-posta doğrulaması gönderilir ve kullanıcı çıkış yapar.
                    authService.sendEmailVerification()
                    authService.signOut()

                    // Durum "Doğrulama e-postası gönderildi" olarak güncellenir.
                    _authState.value =
                        AuthState.Success("Doğrulama E-postası Gönderildi. Lütfen E-Posta Adresinizi Kontrol Ediniz.")
                } else {
                    // Kayıt başarısızsa
                    _authState.value = AuthState.Error("Kayıt başarısız.")


                }
            }
    }



    fun resetPassword(email: String){
        _authState.value = AuthState.Loading

        // AuthService'den şifre sıfırlama işlemi çağrılır.
        authService.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    // Şifre sıfırlama işlemi başarılıysa
                    _authState.value = AuthState.Success("Şifre Sıfırlama E-Postası Gönderildi")
                } else {
                    // Şifre sıfırlama işlemi başarısızsa,
                    _authState.value = AuthState.Error("Şifre Sıfırlama E-Postası Gönderilemedi.")
                }
            }
    }


    // Kullanıcı çıkışı için işlemleri başlatır.
    fun signout(){

        // AuthService'den çıkış işlemi yapılır.
        authService.signOut()

        // Çıkış işleminden sonra, durum "Unauthenticated" olarak güncellenir
        _authState.value = AuthState.Unauthenticated
    }

    sealed class AuthState  {
        object Authenicated : AuthState() // Kullanıcı doğrulandı.
        object Unauthenticated : AuthState() // Kullanıcı doğrulanmamış veya çıkış yaptı.
        object Loading : AuthState() // İşlem yapılıyor.
        data class Success(val message: String) : AuthState() // Başarı durumu.
        data class Error(val message: String) : AuthState() // Hata durumu.
    }

}