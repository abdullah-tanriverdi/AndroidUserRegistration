package com.tanriverdi.card2qr.onboardingscreen

import androidx.annotation.DrawableRes
import com.tanriverdi.card2qr.R

//Farklı onboarding ekranlarını temsil eden sealed class
sealed class OnboardingModel (
     @DrawableRes val image: Int,
     val title: String,
     val description: String,){


    //Belirli bir resim, başlık ve açıklama ile onboarding sayfaları

     data object FirstPage : OnboardingModel(
         image = R.drawable.card2qr,
         title = " Page 1",
         description = "First Description"
     )

     data object SecondPage : OnboardingModel(
         image = R.drawable.card2qr,
         title = " Page 2",
         description = "Second Description"
     )

     data object ThirdPage : OnboardingModel(
         image = R.drawable.card2qr,
         title = " Page 3",
         description = "Third Description"
     )


}