package com.mctech.pokergrinder.design.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Declares the app theme provider with colors and typographies.
 */
object PokerGrinder {

    /**
     * Holds all available colors on the app.
     */
    val LocalColors = staticCompositionLocalOf { lightSpaceXColors }

    /**
     * Holds all the available typographies on the app.
     */
    val LocalTypography = staticCompositionLocalOf { PokerGrinderTypography(lightSpaceXColors) }

    /**
     * Application composable theme.
     */
    @Composable
    fun PokerGrinderTheme(
        isDarkMode: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        // Sets the color pallet based on system theme.
        val colors = if (isDarkMode) darkSpaceXColors else lightSpaceXColors

        // Sets the composition local providers with colors and typographies
        CompositionLocalProvider(
            LocalColors provides colors,
            LocalTypography provides PokerGrinderTypography(colors),
            content = content
        )
    }
}
