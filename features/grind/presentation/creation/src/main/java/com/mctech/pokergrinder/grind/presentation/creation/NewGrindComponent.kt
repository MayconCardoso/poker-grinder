package com.mctech.pokergrinder.grind.presentation.creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.grind.presentation.creation.composables.NewGrindUi
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation

@Composable
internal fun NewGrindComponent(
  navigation: GrindNavigation,
  viewModel: NewGrindViewModel = hiltViewModel(),
) {
  // Gets view model
  val state = viewModel.componentState.collectAsState().value

  // Observe commands
  val command = viewModel.commandObservable.observeAsState().value
  if (command == NewGrindCommand.CloseScreen) {
    navigation.navigateBack()
    return
  }

  // Draw component
  NewGrindUi(state = state) { userInteraction ->
    viewModel.interact(userInteraction)
  }
}