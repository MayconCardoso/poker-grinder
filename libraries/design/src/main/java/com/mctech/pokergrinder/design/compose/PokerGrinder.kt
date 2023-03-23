package com.mctech.pokergrinder.design.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Declares the app theme provider with colors and typographies.
 */
object PokerGrinder {

  /**
   * Holds all available colors on the app.
   */
  lateinit var LocalColors: ProvidableCompositionLocal<PokerGrinderColors>

  /**
   * Holds all the available typographies on the app.
   */
  lateinit var LocalTypography: ProvidableCompositionLocal<Typography>

  /**
   * Application composable theme.
   */
  @Composable
  fun PokerGrinderTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
  ) {
    // Sets the color pallet based on system theme.
    val colors = if (isDarkMode) darkColors else lightColors
    val materialColors = if (isDarkMode) darkMaterialColors else lightMaterialColors

    // Sets the project typography.
    val typography = TypographyFactory(colors = colors)

    // Sets the project providers.
    LocalColors = staticCompositionLocalOf { colors }
    LocalTypography = staticCompositionLocalOf { typography }

    // Sets the composition local providers with colors and typographies
    MaterialTheme(colors = materialColors, typography = typography) {
      CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        content = content
      )
    }
  }
}

@Composable
fun colorProvider() = PokerGrinder.LocalColors.current

@Composable
fun typoProvider() = PokerGrinder.LocalTypography.current