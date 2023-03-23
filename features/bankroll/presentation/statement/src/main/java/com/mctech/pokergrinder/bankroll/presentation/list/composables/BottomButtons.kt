package com.mctech.pokergrinder.bankroll.presentation.list.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollComponentEvent
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollComponentEventConsumer
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R

@Composable
internal fun BottomButtons(
  consumer: BankrollComponentEventConsumer,
  modifier: Modifier = Modifier
) {
  // Creates the row for drawing both buttons.
  Row(
    modifier = modifier.fillMaxWidth()
  ) {
    // Creates deposit button
    BottomButton(text = stringResource(id = R.string.deposit)) {
      consumer.onNewEvent(BankrollComponentEvent.OnDepositClicked)
    }

    // Creates space between buttons
    Spacer(modifier = Modifier.width(12.dp))

    // Creates withdraw button
    BottomButton(text = stringResource(id = R.string.withdraw)) {
      consumer.onNewEvent(BankrollComponentEvent.OnWithDrawClicked)
    }
  }
}

@Composable
internal fun RowScope.BottomButton(
  text: String,
  onClick: () -> Unit,
) {
  Button(onClick = onClick, modifier = Modifier.weight(0.5F)) {
    Text(text = text.uppercase(), style = typoProvider().button)
  }
}

@Preview(showBackground = true)
@Composable
internal fun BottomButtonsPreview() {
  PokerGrinder.PokerGrinderTheme {
    BottomButtons(
      consumer = {
        // STUB
      }
    )
  }
}