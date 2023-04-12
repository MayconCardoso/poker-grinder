package com.mctech.pokergrinder.ranges_practice.presentation.list.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.list.RangePracticeListInteraction

@Composable
internal fun NoResultsUi(
  message: String,
  interact: (RangePracticeListInteraction) -> Unit = {},
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
    BottomButtons(interact = interact, modifier = Modifier.align(Alignment.BottomCenter))
  }
}

@Preview(showBackground = true)
@Composable
internal fun NoResultsPreview() {
  PokerGrinder.PokerGrinderTheme {
    NoResultsUi(
      message = stringResource(id = R.string.no_practice),
    )
  }
}