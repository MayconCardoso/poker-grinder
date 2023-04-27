package com.mctech.pokergrinder.bankroll.presentation.balance_component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.components.HeaderToolbarUi
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.extension.clickableWithoutRipple
import com.mctech.pokergrinder.localization.R

@Composable
fun BankrollBalanceComponent(
  modifier: Modifier = Modifier
) {
  // Gets view model
  val viewModel = hiltViewModel<BankrollBalanceViewModel>().apply {
    initialize()
  }

  // Gets state
  val state = viewModel.balanceState.collectAsState().value

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Error,
    is ComponentState.Loading -> BankrollBalance(
      balance = stringResource(id = R.string.hyphen),
      modifier = modifier,
    )

    is ComponentState.Success -> BankrollBalance(
      balance = state.result,
      modifier = modifier,
      interact = { interaction ->
        viewModel.interact(interaction )
      }
    )
  }
}

@Composable
internal fun BankrollBalance(
  modifier: Modifier = Modifier,
  balance: String,
  interact: (BankrollBalanceInteraction) -> Unit = {},
) {
  HeaderToolbarUi(
    title = stringResource(id = R.string.bankroll),
    subtitle = balance,
    modifier = modifier.clickableWithoutRipple {
      interact(BankrollBalanceInteraction.OnBalanceClicked)
    },
  )
}

@Preview(showBackground = true)
@Composable
internal fun BankrollBalanceComponentPreview() {
  PokerGrinder.PokerGrinderTheme {
    BankrollBalance(balance = "$2100.00")
  }
}