package com.tanriverdi.card2qr.onboardingscreen

import android.content.Context

// Onboarding sürecini yönetmek için kullanılan yardımcı sınıf
class OnboardingUtils(private val context: Context) {


    // Onboarding sürecinin tamamlanıp tamamlanmadığını kontrol eden fonksiyon
    fun isOnboardingCompleted(): Boolean {
        // SharedPreferences kullanarak 'completed' anahtarına karşılık gelen değeri döndürür
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false)// Varsayılan değer olarak false döner
    }

    // Onboarding sürecinin tamamlandığını ayarlayan fonksiyon
    fun setOnboardingCompleted() {
        // SharedPreferences kullanarak 'completed' anahtarını true olarak ayarlar
        context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)  // 'completed' anahtarını true olarak günceller
            .apply() //Değişiklikleri hemen uygular
    }

}