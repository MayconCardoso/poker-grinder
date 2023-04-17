package com.mctech.pokergrinder.ranges_practice.presentation.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.design.components.EmptyContentUi
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.filter.composables.FilterUi
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation

@Composable
fun RangePracticeFilterComponent(
  navigation: RangePracticeNavigation,
) {
  // Gets view practice model
  val viewModel = hiltViewModel<RangePracticeFilterViewModel>().apply {
    initialize()
  }

  // Creates event consumer.
  val eventConsumer: (RangePracticeFilterInteraction) -> Unit = { userInteraction ->
    viewModel.interact(userInteraction)
  }

  // Gets state
  val state = viewModel.state.collectAsState().value

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Error -> EmptyContentUi(
      message = stringResource(id = R.string.not_possible_load_filters),
    )
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Success -> FilterUi(
      state = state.result,
      interact = eventConsumer,
    )
  }

  // Gets commands
  val command = viewModel.commandObservable.observeAsState().value
  when (command) {
    is RangePracticeFilterCommands.NavigateBack -> navigation.navigateBack()
  }
}