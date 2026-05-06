package com.offmind.aiflappybird.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CogwingDarkColorScheme = darkColorScheme(
    primary = Brass,
    onPrimary = InkDark,
    primaryContainer = BrassDark,
    onPrimaryContainer = BrassLight,
    secondary = Copper,
    onSecondary = InkDark,
    secondaryContainer = CopperDark,
    onSecondaryContainer = CopperLight,
    tertiary = Verdigris,
    onTertiary = ParchmentLight,
    background = SootDark,
    onBackground = ParchmentLight,
    surface = Soot,
    onSurface = ParchmentLight,
    surfaceVariant = SootLight,
    onSurfaceVariant = ParchmentDark,
    error = Ember,
    onError = ParchmentLight,
)

private val CogwingLightColorScheme = lightColorScheme(
    primary = BrassDark,
    onPrimary = ParchmentLight,
    primaryContainer = BrassLight,
    onPrimaryContainer = BrassDark,
    secondary = CopperDark,
    onSecondary = ParchmentLight,
    secondaryContainer = CopperLight,
    onSecondaryContainer = CopperDark,
    tertiary = Verdigris,
    onTertiary = ParchmentLight,
    background = ParchmentLight,
    onBackground = InkDark,
    surface = Parchment,
    onSurface = InkDark,
    surfaceVariant = ParchmentDark,
    onSurfaceVariant = Ink,
    error = Ember,
    onError = ParchmentLight,
)

@Composable
fun CogwingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CogwingDarkColorScheme else CogwingLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CogwingTypography,
        shapes = CogwingShapes,
        content = content,
    )
}
