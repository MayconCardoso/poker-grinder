package com.mctech.pokergrinder.design.compose

import android.graphics.Color.parseColor
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
    val textHighlighted: Color,
    val background: Color,
    val itemBackground: Color,
    val dialogWindowBackground: Color,
    val bottomNavBackground: Color,
    val bottomTrayBackground: Color,
    val statusBar: Color,
    val selectedTab: Color,
    val unselectedTab: Color
)

/**
 * Declares the light colors of the app.
 * It will be defined in a future implementation.
 */
val lightSpaceXColors: PokerGrinderColors = PokerGrinderColors(
    primary = Color(parseColor("#F44336")),
    primaryDark = Color(parseColor("#D32F2F")),
    primaryLight = Color(parseColor("#FFCDD2")),

    accent = Color(parseColor("#795548")),
    accentDark = Color(parseColor("#5D4037")),
    accentLight = Color(parseColor("#D7CCC8")),

    textColorPrimary = Color(parseColor("#000000")),
    textColorSecondary = Color(parseColor("#7F7F82")),
    textColorInverted = Color.White,
    textHighlighted = Color(parseColor("#58B5E5")),

    background = Color(parseColor("#E3E6E9")),
    itemBackground = Color(parseColor("#FFFFFF")),
    dialogWindowBackground = Color(parseColor("#E0E5E9")),
    bottomNavBackground = Color(parseColor("#BBC3C8")),
    bottomTrayBackground = Color(parseColor("#F7F7F7")),
    statusBar = Color(parseColor("#ADB4B9")),
    selectedTab = Color(parseColor("#00A2EA")),
    unselectedTab = Color(parseColor("#7F7F82")),
)

/**
 * Declares the dark colors of the app.
 * It will be defined in a future implementation.
 */
val darkSpaceXColors: PokerGrinderColors = lightSpaceXColors
