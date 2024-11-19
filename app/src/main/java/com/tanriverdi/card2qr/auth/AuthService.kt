package com.tanriverdi.card2qr.auth

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


/* Authentication Manager
MVMM mimarisine uygun model sınıfı
 * Bu Service, Firebase Authentication işlemlerini yönetir.
 * Firebase ile ilgili tüm işlemler AuthService sınıfı içinde kapsüllenmiştir.
 */




// AuthService sınıfı, Firebase Authentication ile kullanıcı işlemlerini yönetir.
class AuthService(private val auth: FirebaseAuth) {


    // İşlemler başarılı olduğunda, bir Task<AuthResult> döner.


    // Kullanıcıyı e-posta ve şifre ile giriş yapmaya yönlendirir.
    fun signInWithEmail(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email,password)
    }

    // Yeni bir kullanıcı oluşturur. E-posta ve şifre ile kayıt işlemi yapar.
    fun createUserWithEmail(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email,password)
    }

    // Şifre sıfırlama e-postası gönderir.
    fun sendPasswordResetEmail(email: String): Task<Void> {
        return auth.sendPasswordResetEmail(email)

    }


    // E-posta doğrulama işlemi başlatır.
    fun sendEmailVerification(): Task<Void> {
        return auth.currentUser?.sendEmailVerification() ?: Tasks.forException(Exception("Kullanıcı oturum açmadı"))
    }

    // Kullanıcıyı oturumdan çıkartır.
    fun signOut(){
        auth.signOut()
    }

    // Şu anda giriş yapmış olan kullanıcıyı döndürür. Eğer kullanıcı yoksa, null döner.
    fun getCurrentUser() : FirebaseUser?{
        return  auth.currentUser
    }
}

