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
fun RangePracticeComponent(
  navigation: RangePracticeNavigation,
) {
  // Gets view practice model
  val viewModel = hiltViewModel<RangePracticeViewModel>().apply {
    initialize()
  }

  // Creates event consumer.
  val eventConsumer: (RangePracticeInteraction) -> Unit = { userInteraction ->
    when(userInteraction) {
      RangePracticeInteraction.OnFilterClicked -> navigation.goToRangePracticeFilter()
      else -> viewModel.interact(userInteraction)
    }
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