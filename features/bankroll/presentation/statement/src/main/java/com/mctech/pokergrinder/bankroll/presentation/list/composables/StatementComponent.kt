package com.mctech.pokergrinder.bankroll.presentation.list.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.presentation.list.StatementInteraction
import com.mctech.pokergrinder.bankroll.presentation.list.StatementViewModel
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.localization.R

@Composable
fun BankrollTransactionComponent(
  navigation: BankrollNavigation,
) {
  // Gets view model
  val viewModel = hiltViewModel<StatementViewModel>().apply {
    initialize()
  }

  // Gets state
  val state = viewModel.transactionState.collectAsState().value

  // Creates event consumer.
  val eventConsumer: (StatementInteraction) -> Unit = { userInteraction ->
    when (userInteraction) {
      is StatementInteraction.OnDepositClicked -> navigation.goToBankrollDeposit()
      is StatementInteraction.OnWithDrawClicked -> navigation.goToBankrollWithdraw()
    }
  }

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Error -> NoResultsUi(
      message = stringResource(id = R.string.something_went_wrong),
      interact = eventConsumer,
    )
    is ComponentState.Success -> TransactionsUi(
      items = state.result,
      interact = eventConsumer,
    )
  }
}

@Composable
internal fun TransactionsUi(
  items: List<BankrollTransaction>,
  interact: (StatementInteraction) -> Unit = {},
) {
  // Draws empty list text if there is no transactions
  if (items.isEmpty()) {
    NoResultsUi(
      message = stringResource(id = R.string.no_transaction),
      interact = interact,
    )
    return
  }

  // Draws transactions items.
  TransactionList(items = items, interact = interact)
}