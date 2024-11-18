package com.tanriverdi.card2qr.onboardingscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorUI(
    pageSize: Int, //Gösterge noktalarının sayısı
    currentPage: Int, //Aktif sayfa numarası
    selectedColor: Color = MaterialTheme.colorScheme.surface, // Seçili gösterge için renk
    unselectedColor: Color = MaterialTheme.colorScheme.primary // Seçili olmayan gösterge için renk



)
{
    Row (horizontalArrangement =  Arrangement.SpaceBetween){
        //Gösterge noktalarını tekrarlayarak her biri için bir Box oluşturma

        repeat(pageSize){ index->

            // Seçili gösterge genişken, diğerleri daha dar
            Box(modifier = Modifier
                .height(18.dp) // Gösterge yüksekliği
                .width(width = if(index==currentPage) 38.dp else 18.dp)   // Seçili gösterge genişken, diğerleri küçük
                .clip(RoundedCornerShape(10.dp))  // Yuvarlak köşe şekli
                .background(color = if(index == currentPage) selectedColor else unselectedColor) // Renk atama
            )

            // Gösterge kutuları arasında boşluk bırakmak için Spacer
            Spacer(modifier = Modifier.size(2.5.dp))
        }
    }

}


// Önizleme bileşeni
@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview1() {

    IndicatorUI(pageSize = 3, currentPage = 0)

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview2() {

    IndicatorUI(pageSize = 3, currentPage = 1)

}

@Preview(showBackground = true)
@Composable
fun IndicatorUIPreview3() {

    IndicatorUI(pageSize = 3, currentPage = 2)

}