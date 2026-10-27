package com.sistema.sistematicket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Claro = lightColorScheme(

    primary = Color(0xFF2E7F88),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCADFE2),
    onPrimaryContainer = Color(0xFF0F3A3F),

    secondary = Color(0xFFA8475A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE6CFD3),
    onSecondaryContainer = Color(0xFF3E1520),

    tertiary = Color(0xFF7D6240),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE3D8C4),
    onTertiaryContainer = Color(0xFF2E1A00),

    background = Color(0xFFDDE3E4),
    onBackground = Color(0xFF1F2526),
    surface = Color(0xFFDDE3E4),
    onSurface = Color(0xFF1F2526),
    surfaceVariant = Color(0xFFCBD4D5),
    onSurfaceVariant = Color(0xFF3F4849),

    surfaceContainerLowest = Color(0xFFD5DBDC),
    surfaceContainerLow = Color(0xFFD9DFE0),
    surfaceContainer = Color(0xFFD2D9DA),
    surfaceContainerHigh = Color(0xFFCDD5D6),
    surfaceContainerHighest = Color(0xFFC8D0D1)
)

private val Oscuro = darkColorScheme(
    primary = Color(0xFF4DD9E6),
    onPrimary = Color(0xFF00363B),
    primaryContainer = Color(0xFF004F56),
    onPrimaryContainer = Color(0xFFC2FBFF),

    secondary = Color(0xFFFFB2BD),
    onSecondary = Color(0xFF680020),
    secondaryContainer = Color(0xFF8F1F38),
    onSecondaryContainer = Color(0xFFFFD9DE),

    tertiary = Color(0xFFE6BE8A),
    onTertiary = Color(0xFF442B00),
    tertiaryContainer = Color(0xFF5E4220),
    onTertiaryContainer = Color(0xFFF7DDB2),

    background = Color(0xFF0E1415),
    onBackground = Color(0xFFDEE3E4),
    surface = Color(0xFF0E1415),
    onSurface = Color(0xFFDEE3E4),
    surfaceVariant = Color(0xFF3F4849),
    onSurfaceVariant = Color(0xFFBFC8CA)
)

@Composable
fun MarcelaPazTicketTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) Oscuro else Claro,
        content = content
    )
}