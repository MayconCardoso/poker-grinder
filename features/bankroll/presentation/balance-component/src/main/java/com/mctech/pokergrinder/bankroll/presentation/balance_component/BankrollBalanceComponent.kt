package com.mctech.pokergrinder.bankroll.presentation.balance_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.PokerGrinder.LocalColors
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
    )
  }
}

@Composable
internal fun BankrollBalance(
  balance: String,
  modifier: Modifier = Modifier
) {
  // Draws the outer card.
  Column(
    modifier
      .background(LocalColors.current.primary)
      .fillMaxWidth()
      .padding(start = 15.dp, top = 4.dp, bottom = 6.dp, end = 6.dp)
  ) {

    // Draws the title
    Text(
      text = stringResource(id = R.string.bankroll),
      style = TextStyle(
        color = LocalColors.current.textColorInverted,
        fontSize = 19.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
      ),
    )

    // Draws the Balance
    Text(
      text = balance,
      style = TextStyle(
        color = LocalColors.current.textColorInverted,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
      ),
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun BankrollBalanceComponentPreview() {
  PokerGrinder.PokerGrinderTheme() {
    BankrollBalance(balance = "R$2100.00")
  }
}