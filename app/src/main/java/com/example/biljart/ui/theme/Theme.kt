package com.example.biljart.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Green700,
    secondary = Blue500,
    tertiary = Amber500,
    background = LightBackground,
    surface = LightBackground,
    onPrimary = ContentColorOnPrimaryLightMode, // Content color on primary
    onSecondary = ContentColorOnSecondaryLightMode, // Content color on secondary
    onTertiary = ContentColorOnTertiaryLightMode, // Content color on tertiary
    onBackground = LightOnBackground, // Content color on background
    onSurface = LightOnBackground, // Content color on surface
    primaryContainer = Green500,
    onPrimaryContainer = ContentColorOnPrimaryLightMode,
    error = Amber700,
)

private val DarkColorScheme = darkColorScheme(
    primary = Green700,
    secondary = Green700,
    tertiary = Amber700,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = ContentColorOnPrimaryDarkMode, // Content color on primary
    onSecondary = ContentColorOnSecondaryDarkMode, // Content color on secondary
    onTertiary = ContentColorOnTertiaryDarkMode, // Content color on tertiary
    onBackground = DarkOnBackground, // Content color on background
    onSurface = DarkOnBackground, // Content color on surface
    primaryContainer = Green700,
    onPrimaryContainer = DarkOnBackground,
    error = Amber700,
)

@Composable
fun BilliardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+, put to false because not available in this app
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme( // Purpose: to apply the Material Design theme to the app with the colorScheme and typography defined in the theme
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
