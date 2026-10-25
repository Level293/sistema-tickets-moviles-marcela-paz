package com.sistema.sistematicket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Claro = lightColorScheme(
    primary = Color(0xFF3F51B5),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDE1FF),
    onPrimaryContainer = Color(0xFF00105C),
    secondary = Color(0xFF00897B),
    secondaryContainer = Color(0xFFB2DFDB)
)

private val Oscuro = darkColorScheme(
    primary = Color(0xFFB9C3FF),
    onPrimary = Color(0xFF08218A),
    primaryContainer = Color(0xFF283593),
    onPrimaryContainer = Color(0xFFDDE1FF),
    secondary = Color(0xFF80CBC4),
    secondaryContainer = Color(0xFF00695C)
)

@Composable
fun MarcelaPazTicketTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) Oscuro else Claro,
        content = content
    )
}