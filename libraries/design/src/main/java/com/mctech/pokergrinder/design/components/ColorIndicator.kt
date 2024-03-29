package com.mctech.pokergrinder.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.colorProvider

@Composable
fun ColorIndicator(
  success: Boolean,
  modifier: Modifier = Modifier,
) {
  // Resolve the right color.
  val color = if (success) colorProvider().positiveIndicator else colorProvider().negativeIndicator

  // Renders the background.
  Spacer(
    modifier = modifier
      .width(5.dp)
      .fillMaxHeight()
      .background(color)
  )
}

@Preview(showBackground = true)
@Composable
internal fun NegativeColorIndicatorPreview() {
  DynamicColorIndicatorPreview(success = false)
}

@Preview(showBackground = true)
@Composable
internal fun PositiveColorIndicatorPreview() {
  DynamicColorIndicatorPreview(success = true)
}

@Composable
internal fun DynamicColorIndicatorPreview(success: Boolean) {
  PokerGrinder.PokerGrinderTheme {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
      ColorIndicator(success = success)
      Column {
        Text(text = "Hello")
        Text(text = "World")
      }
      Spacer(modifier = Modifier.width(20.dp))
      Column {
        Text(text = "Hello")
        Text(text = "World")
      }
    }
  }
}