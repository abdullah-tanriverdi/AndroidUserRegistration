package com.tanriverdi.card2qr.loginscreen

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth



// AuthViewModel, kullanıcının giriş durumu ve kimlik doğrulama işlemleriyle ilgilenen bir ViewModel sınıfıdır.
class AuthViewModel : ViewModel() {

    // FirebaseAuth örneği, kullanıcı kimlik doğrulama işlemlerini yönetmek için kullanılır.
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // _authState, uygulamanın kimlik doğrulama durumunu tutar ve sadece ViewModel içinde erişilebilir.
    private val _authState = MutableLiveData<AuthState>()

    // authState, _authState'i dışarıya sadece okunabilir şekilde serbest bırakır.
    val authState: LiveData<AuthState> = _authState

    // ViewModel başlatıldığında, kullanıcı kimlik doğrulama durumunu kontrol etmek için checkAuthStatus fonksiyonu çağrılır.
    init {
        checkAuthStatus()
    }

    // checkAuthStatus fonksiyonu, FirebaseAuth kullanarak kullanıcının giriş yapıp yapmadığını kontrol eder.
    fun checkAuthStatus(){

        val user = auth.currentUser
        if (user == null) {
            // Kullanıcı giriş yapmamışsa, kimlik doğrulama durumu "Unauthenticated" olarak güncellenir.
            _authState.value = AuthState.Unauthenticated
        } else if (user.isEmailVerified) {
            // Doğrulama yapılmışsa, kullanıcıya giriş yapılması izin verilir
            _authState.value = AuthState.Authenicated
        } else {
            // E-posta doğrulama yapılmamışsa, hata durumu oluşturulur
            _authState.value = AuthState.Error("Hesabınız Doğrulanmamış. Lütfen E-Posta Adresinizi Kontrol Edin.")
        }

    }

    // login fonksiyonu, kullanıcının e-posta ve şifresi ile giriş yapmasını sağlar.
    fun login (email : String,password : String){
        // E-posta veya şifre boşsa, hata durumu belirlenir.
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("E-Posta Ya Da Parola Boş Olamaz!")
            return
        }

        // Geçerli bir e-posta adresi olup olmadığını kontrol et
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _authState.value = AuthState.Error("Geçerli Bir E-Posta Adresi Girin!")
            return
        }

        // Giriş işlemi başlatıldığında, durum "Loading" olarak güncellenir
        _authState.value =AuthState.Loading

        // Firebase'de giriş işlemi yapılır.
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    if (user?.isEmailVerified==true){
                        // İşlem başarılıysa, kullanıcı doğrulandı olarak işaretlenir.
                        _authState.value=AuthState.Authenicated
                    }else{
                        // E-posta doğrulama yapılmamışsa hata durumu oluşturulur
                        _authState.value = AuthState.Error("Hesabınız Doğrulanmamış. Lütfen E-Posta Adresinizi Kontrol Edin.")                    }
                }else{
                    // İşlem başarısızsa, hata mesajı görüntülenir.
                    _authState.value  = AuthState.Error(task.exception?.message?:"Bir Şeyler Ters Gitti" )
                }
            }
    }


    // signup fonksiyonu, kullanıcının yeni bir hesap oluşturmasına olanak sağlar.
    fun signup (email : String,password : String){
        // E-posta veya şifre boşsa, hata durumu belirlenir.
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("E-Posta Ya Da Parola Boş Olamaz!")
            return
        }

        // Geçerli bir e-posta adresi olup olmadığını kontrol et
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _authState.value = AuthState.Error("Geçerli Bir E-Posta Adresi Girin!")
            return
        }

        // Kayıt işlemi başlatıldığında, durum "Loading" olarak güncellenir.
        _authState.value =AuthState.Loading

        // Firebase'de hesap oluşturulması için işlem başlatılır.
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                // İşlem başarılıysa, kullanıcı doğrulandı olarak işaretlenir.
                if(task.isSuccessful){
                    sendEmailVerification() // Kullanıcıya doğrulama e-postası gönderilir.
                    auth.signOut() // Hesap oluşturulduktan sonra kullanıcı çıkış yapar.
                }else{
                    // İşlem başarısızsa, hata mesajı görüntülenir.
                    _authState.value  = AuthState.Error(task.exception?.message?:"Bir Şeyler Ters Gitti")
                }
            }
    }

    // Kullanıcıya doğrulama e-postası gönderen fonksiyon.
    private fun sendEmailVerification(){
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task->
                if (task.isSuccessful){
                    // E-posta gönderimi başarılıysa, durum güncellenir.
                    _authState.value = AuthState.Success("Doğrulama E-postası Gönderildi. Lütfen E-Posta Adresinizi Kontrol Ediniz.")
                }else{
                    // E-posta gönderimi başarısızsa, hata mesajı gösterilir.
                    _authState.value = AuthState.Error("E-Posta Gönderilemedi: ${task.exception?.message}")
                }
            }
    }






    // signout fonksiyonu, kullanıcının çıkış yapmasını sağlar.
    fun signout(){
        auth.signOut()
        // Çıkış işleminden sonra, durum "Unauthenticated" olarak güncellenir.
        _authState.value= AuthState.Unauthenticated
    }

    // Şifre sıfırlama fonksiyonu.
    fun resetPassword(email: String){
        if (email.isEmpty()){
            _authState.value= AuthState.Error("E-Posta Boş Olamaz!")
            return
        }

        _authState.value = AuthState.Loading
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    _authState.value= AuthState.Success("Şifre Sıfırlama E-Postası Gönderildi")
                }else{
                    // Şifre sıfırlama e-postası gönderilemezse hata mesajı görüntülenir.
                    _authState.value = AuthState.Error(task.exception?.message ?: "Bir Şeyler Ters Gitti")

                }
            }
    }

}
// AuthState, kullanıcı kimlik doğrulama durumlarını temsil eden sealed class'tır.
sealed class AuthState {
    object Authenicated : AuthState() // Kullanıcı doğrulandı.
    object Unauthenticated : AuthState() // Kullanıcı doğrulanmamış veya çıkış yaptı.
    object Loading : AuthState() // İşlem yapılıyor.
    data class Error(val message: String) : AuthState() // Hata durumu.
    data class Success(val message: String) : AuthState() // Başarı durumu.
}