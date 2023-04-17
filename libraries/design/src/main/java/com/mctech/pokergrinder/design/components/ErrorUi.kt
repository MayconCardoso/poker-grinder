package com.mctech.pokergrinder.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen

@Composable
fun EmptyContentUi(
  message: String,
  buttonsContent: @Composable BoxScope.() -> Unit = {},
) {
  // Draws the outer card.
  Box(modifier = Modifier.fillScreen()) {
    // Draw informative text
    Text(
      text = message,
      textAlign = TextAlign.Center,
      style = typoProvider().h2,
      modifier = Modifier.align(Alignment.Center)
    )

    // Draw bottom buttons
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth(),
    ) {
      buttonsContent()
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun ErrorUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    EmptyContentUi(
      message = "It could not be loaded",
    )
  }
}