package com.tanriverdi.card2qr.onboardingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingGraphUI (onboardingModel: OnboardingModel) {


    //Elemanları dikey olarak sıralamak için Column bileşeni
    Column (modifier = Modifier.fillMaxWidth()){
        //Üst kısmına boşluk eklemek için Spacer bileşeni
        Spacer(
            modifier = Modifier
                .size(50.dp)
        )

        //Onboarding görselini göstermek için Image bileşeni
        Image(
            painter = painterResource(id=onboardingModel.image), //Kaynak ID'den görsel yüklenir
            contentDescription = null, //Erişilebilirlik için açıklama metni eklenmedi
            modifier = Modifier
                .fillMaxWidth() //Görseli tam genişlikte yer kaplar
                .padding(50.dp,0.dp), //Görselin çevresine yatay dolgu eklenir
            alignment = Alignment.Center //Görsel ortalanır

        )


        //Görsel ile başlık metni arasında boşluk eklemek için Spacer bileşeni
        Spacer(
            modifier = Modifier
                .size(50.dp)
        )

        //Onboarding sayfasının başlığını göstermek için Text bileşeni
        Text(
            text = onboardingModel.title, // Başlık metni onboardingModel'den alınır
            modifier = Modifier.fillMaxWidth(), // Metin tam genişlikte yer kaplar
            fontSize = 20.sp, // Başlık metninin font boyutu
            textAlign = TextAlign.Center, // Başlık metnini ortalar
            style = MaterialTheme.typography.titleMedium,  // Temadan tipografi stili uygulanır
            color = MaterialTheme.colorScheme.onBackground    // Temanın renk şemasından renk seçilir
        )

        // Başlık ile açıklama metni arasına boşluk eklemek için Spacer bileşeni
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(10.dp)
        )

        // Başlık ile açıklama metni arasına boşluk eklemek için Spacer bileşeni
        Text(
            text = onboardingModel.description, // Açıklama metni onboardingModel'den alınır
            modifier = Modifier
                .fillMaxWidth() // Metin tam genişlikte yer kaplar
                .padding(15.dp,0.dp),// Açıklama metnine yatay dolgu ekleniyor
            fontSize = 16.sp,// Açıklama metninin font boyutu
            textAlign = TextAlign.Center,// Açıklama metnini ortalar
            style = MaterialTheme.typography.bodySmall,// Temadan tipografi stili uygulanır
            color= MaterialTheme.colorScheme.onSurface, // Temanın yüzey rengi seçilir
        )

        // Açıklama metninden sonra boşluk bırakmak için Spacer bileşeni
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(10.dp)
        )


    }


}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview1() {
    OnboardingGraphUI(OnboardingModel.FirstPage)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview2() {
    OnboardingGraphUI(OnboardingModel.SecondPage)
}

@Preview(showBackground = true)
@Composable
fun OnboardingGraphUIPreview3() {
    OnboardingGraphUI(OnboardingModel.ThirdPage)
}