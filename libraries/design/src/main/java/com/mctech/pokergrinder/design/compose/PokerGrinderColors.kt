package com.mctech.pokergrinder.design.compose

import android.graphics.Color.parseColor
import androidx.compose.material.lightColors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PokerGrinderColors(
  val primary: Color,
  val primaryDark: Color,
  val primaryLight: Color,
  val accent: Color,
  val accentDark: Color,
  val accentLight: Color,
  val textColorPrimary: Color,
  val textColorSecondary: Color,
  val textColorInverted: Color,
  val positiveIndicator: Color,
  val negativeIndicator: Color,
)

/**
 * Declares the light colors of the app.
 */
val lightColors: PokerGrinderColors = PokerGrinderColors(
  primary = Color(parseColor("#F44336")),
  primaryDark = Color(parseColor("#D32F2F")),
  primaryLight = Color(parseColor("#FFCDD2")),

  accent = Color(parseColor("#795548")),
  accentDark = Color(parseColor("#5D4037")),
  accentLight = Color(parseColor("#D7CCC8")),

  textColorPrimary = Color(parseColor("#000000")),
  textColorSecondary = Color(parseColor("#7F7F82")),
  textColorInverted = Color.White,

  positiveIndicator = Color(parseColor("#4CAF50")),
  negativeIndicator = Color(parseColor("#F44336")),
)

/**
 * Declares the light colors of the app.
 */
val lightMaterialColors = lightColors(
  primary = lightColors.primary,
  primaryVariant = lightColors.primaryDark,
  secondary = lightColors.accent,
  secondaryVariant = lightColors.accentDark,
)

/**
 * Declares the dark colors of the app.
 * It will be defined in a future implementation.
 */
val darkColors: PokerGrinderColors = lightColors

/**
 * Declares the dark colors of the app.
 * It will be defined in a future implementation.
 */
val darkMaterialColors = lightMaterialColors
