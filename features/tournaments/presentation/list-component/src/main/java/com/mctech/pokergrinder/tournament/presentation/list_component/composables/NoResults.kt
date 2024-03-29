package com.mctech.pokergrinder.tournament.presentation.list_component.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R

@Composable
internal fun NoResultsUi(
  message: String,
) {
  // Draws the outer card.
  Box(modifier = Modifier.fillScreen()) {
    // Draw informative text
    Text(
      text = message,
      style = typoProvider().h2,
      modifier = Modifier.align(Alignment.Center)
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun NoResultsPreview() {
  PokerGrinder.PokerGrinderTheme {
    NoResultsUi(
      message = stringResource(id = R.string.no_tournament),
    )
  }
}