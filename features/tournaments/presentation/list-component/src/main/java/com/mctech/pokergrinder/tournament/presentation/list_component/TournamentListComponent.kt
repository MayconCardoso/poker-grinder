package com.mctech.pokergrinder.tournament.presentation.list_component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.presentation.list_component.composables.NoResultsUi
import com.mctech.pokergrinder.tournament.presentation.list_component.composables.TransactionList

@Composable
fun TournamentListComponent(
  modifier: Modifier = Modifier,
  viewModel: TournamentListViewModel = hiltViewModel(),
  callback: TournamentListCallback,
) {
  // Gets view model
  viewModel.initialize()

  // Gets state
  val state = viewModel.componentState.collectAsState().value

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Error -> NoResultsUi(
      message = stringResource(id = R.string.something_went_wrong),
    )
    is ComponentState.Success -> TournamentListUi(
      items = state.result,
      callback = callback,
      modifier = modifier,
    )
  }
}

@Composable
internal fun TournamentListUi(
  modifier: Modifier = Modifier,
  items: List<Tournament>,
  callback: TournamentListCallback,
) {
  // Draws empty list text if there is no transactions
  if (items.isEmpty()) {
    NoResultsUi(message = stringResource(id = R.string.no_tournament))
    return
  }

  // Draws transactions items.
  TransactionList(items = items, callback = callback, modifier = modifier)
}