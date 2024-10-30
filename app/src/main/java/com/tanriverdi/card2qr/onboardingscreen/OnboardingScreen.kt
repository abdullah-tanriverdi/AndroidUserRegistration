package com.tanriverdi.card2qr.onboardingscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch



@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {

    // Onboarding sayfalarını tanımlayın
    val pages = listOf(
        OnboardingModel.FirstPage, OnboardingModel.SecondPage, OnboardingModel.ThirdPage
    )

    // Pager durumunu oluşturun
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    // Buton durumunu tanımlayın
    val buttonState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("", "Sonraki")
                1 -> listOf("Geri", "Sonraki")
                2 -> listOf("Geri", "Başla")
                else -> listOf("", "")
            }
        }
    }

    // Coroutine kapsamını tanımlayın
    val scope = rememberCoroutineScope()

    // Scaffold bileşenini oluşturun
    Scaffold( bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) // Row'un dış kenar boşlukları
                .height(56.dp), // Row'un yüksekliği
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Geri butonu
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                if (buttonState.value[0].isNotEmpty()) {
                    Button(
                        onClick = {
                            if (pagerState.currentPage > 0) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Gray
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = buttonState.value[0],  // Geri butonunun metni
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Göstergeler
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage) // Mevcut sayfa göstergesi
            }

            // İleri butonu
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onFinished() // Son sayfadaysa onFinished çağrılır
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = buttonState.value[1], // İleri butonunun metni
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }) {
        Column(Modifier.padding(it)) {
            // Sayfa içeriklerini göstermek için HorizontalPager
            HorizontalPager(state = pagerState) { index ->
                OnboardingGraphUI(onboardingModel = pages[index]) // Sayfa grafik bileşeni
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen {

    }
}
