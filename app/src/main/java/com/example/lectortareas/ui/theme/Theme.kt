package com.example.lectortareas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = AppColors.Accent,
    onPrimary = Color.White,
    primaryContainer = AppColors.AccentSoft,
    onPrimaryContainer = AppColors.AccentStrong,
    secondary = AppColors.AccentStrong,
    onSecondary = Color.White,
    background = AppColors.Background,
    onBackground = AppColors.Ink,
    surface = AppColors.Paper,
    onSurface = AppColors.Ink,
    surfaceVariant = AppColors.AccentSoft,
    onSurfaceVariant = AppColors.InkSoft,
    outline = AppColors.Line,
    error = AppColors.Danger,
    onError = Color.White,
    errorContainer = AppColors.DangerSoft,
    onErrorContainer = AppColors.Danger
)

@Composable
fun LectorTareasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}