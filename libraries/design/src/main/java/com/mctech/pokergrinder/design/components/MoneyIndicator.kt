package com.mctech.pokergrinder.design.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoneyIndicator(
  amount: Double,
  modifier: Modifier = Modifier,
) {
  // Renders the background.
  ColorIndicator(success = amount >= 0, modifier = modifier)
}