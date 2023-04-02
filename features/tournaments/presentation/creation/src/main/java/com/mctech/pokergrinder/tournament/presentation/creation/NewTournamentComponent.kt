package com.mctech.pokergrinder.tournament.presentation.creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.tournament.presentation.creation.composables.NewTournamentUi
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation

@Composable
internal fun NewTournamentComponent(
  navigation: TournamentNavigation,
  viewModel: NewTournamentViewModel = hiltViewModel(),
) {
  // Gets view model
  val tournamentState = viewModel.componentState.collectAsState().value

  // Observe commands
  val command = viewModel.commandObservable.observeAsState().value
  if (command == NewTournamentCommand.CloseScreen) {
    navigation.navigateBack()
    return
  }

  // Draw component
  NewTournamentUi(tournamentState = tournamentState) { userInteraction ->
    viewModel.interact(userInteraction)
  }
}