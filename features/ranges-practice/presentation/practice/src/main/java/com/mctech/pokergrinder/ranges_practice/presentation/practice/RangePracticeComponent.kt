package com.mctech.pokergrinder.ranges_practice.presentation.practice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation
import com.mctech.pokergrinder.ranges_practice.presentation.practice.composables.ErrorUi
import com.mctech.pokergrinder.ranges_practice.presentation.practice.composables.QuestionUi

@Composable
internal fun RangePracticeComponent(
  viewModel: RangePracticeViewModel = hiltViewModel()
) {
  // Gets view practice model
  viewModel.initialize()

  // Creates event consumer.
  val eventConsumer: (RangePracticeInteraction) -> Unit = { userInteraction ->
    viewModel.interact(userInteraction)
  }

  // Gets state
  val state = viewModel.state.collectAsState().value

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Error -> ErrorUi(interact = eventConsumer)
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Success -> QuestionUi(
      state = state.result,
      interact = eventConsumer,
    )
  }
}