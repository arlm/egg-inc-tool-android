package br.com.alexandremarcondes.egginc.companion.ui

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.ColorPalette
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val DarkColorPalette = darkColorPalette(
        primary = purple200,
        primaryVariant = purple700,
        onPrimary = Color.Black,
        secondary = teal200,
        onSecondary = Color.White,
        error = red200
)

private val LightColorPalette = lightColorPalette(
        primary = purple500,
        primaryVariant = purple700,
        onPrimary = Color.White,
        secondary = teal200,
        onSecondary = Color.White,
        error = red800
)

@Composable
val ColorPalette.snackbarAction: Color
    get() = if (isLight) red300 else red700

@Composable
fun EggIncCompanionTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
    )
}