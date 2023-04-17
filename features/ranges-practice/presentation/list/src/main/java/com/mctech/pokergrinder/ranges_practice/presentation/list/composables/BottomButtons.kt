package com.mctech.pokergrinder.ranges_practice.presentation.list.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.list.RangePracticeListInteraction

@Composable
internal fun BottomButtons(
  modifier: Modifier = Modifier,
  interact: (RangePracticeListInteraction) -> Unit = {},
) {
  Button(
    modifier = modifier.fillMaxWidth(),
    onClick = {
      interact(RangePracticeListInteraction.OnStartPracticing)
    },
  ) {
    Text(
      text = stringResource(id = R.string.practice).uppercase(),
      style = typoProvider().button,
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun BottomButtonsPreview() {
  PokerGrinder.PokerGrinderTheme {
    BottomButtons()
  }
}