package com.mctech.pokergrinder.bankroll.presentation.list.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.bankroll.presentation.list.StatementInteraction
import com.mctech.pokergrinder.design.components.EmptyContentUi
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.localization.R

@Composable
internal fun NoResultsUi(
  message: String,
  interact: (StatementInteraction) -> Unit = {},
) {
  EmptyContentUi(
    message = message,
    buttonsContent = {
      BottomButtons(
        interact = interact,
        modifier = Modifier.align(Alignment.BottomCenter)
      )
    }
  )
}

@Preview(showBackground = true)
@Composable
internal fun NoResultsPreview() {
  PokerGrinder.PokerGrinderTheme {
    NoResultsUi(
      message = stringResource(id = R.string.no_transaction),
    )
  }
}