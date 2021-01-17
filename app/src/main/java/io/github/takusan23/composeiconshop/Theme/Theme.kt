package io.github.takusan23.composeiconshop.Compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = colorPrimary,
    primaryVariant = colorLight,
    secondary = colorDark
)

private val LightColorPalette = lightColors(
    primary = colorPrimary,
    primaryVariant = colorLight,
    secondary = colorDark
)

@Composable
fun ComposeIconShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
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