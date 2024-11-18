package com.tanriverdi.card2qr.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Karanlık tema renk paleti
private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue,
    onPrimary = DarkWhite,
    background = DarkBlack,
    onBackground = DarkWhite,
    surface = DarkYellow,
    onSurface = DarkGray,
    surfaceContainer = DarkRed

)

// Aydınlık tema renk paleti
private val LightColorScheme = lightColorScheme(
    primary = LightBlue,
    onPrimary = LightWhite,
    background = LightBlueLight,
    onBackground = LightBlack,
    surface = LightYellow,
    onSurface = LightGray,
    surfaceContainer = LightRed


)

@Composable
fun Card2QRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Dinamik renk desteği Android 12+ cihazlar için
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
