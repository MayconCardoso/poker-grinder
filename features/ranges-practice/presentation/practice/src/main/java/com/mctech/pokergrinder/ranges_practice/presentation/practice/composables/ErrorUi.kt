package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
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
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun ErrorUi(
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  // Draws the outer card.
  Box(modifier = Modifier.fillScreen()) {
    // Draw informative text
    Text(
      text = stringResource(id = R.string.something_went_wrong),
      textAlign = TextAlign.Center,
      style = typoProvider().h2,
      modifier = Modifier.align(Alignment.Center)
    )

    // Draw bottom buttons
    Button(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth(),
      onClick = {
        interact(RangePracticeInteraction.OnNextQuestionClicked)
      },
    ) {
      Text(
        text = stringResource(id = R.string.practice).uppercase(),
        style = typoProvider().button,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun NoResultsPreview() {
  PokerGrinder.PokerGrinderTheme {
    ErrorUi()
  }
}