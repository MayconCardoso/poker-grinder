package com.mctech.pokergrinder.ranges_practice.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import com.mctech.pokergrinder.ranges_practice.presentation.list.composables.NoResultsUi
import com.mctech.pokergrinder.ranges_practice.presentation.list.composables.TrainingAnswerList
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation

@Composable
fun RangePracticeComponent(
  navigation: RangePracticeNavigation,
) {
  // Gets view model
  val viewModel = hiltViewModel<RangePracticeListViewModel>().apply {
    initialize()
  }

  // Gets state
  val state = viewModel.state.collectAsState().value

  // Creates event consumer.
  val eventConsumer: (RangePracticeListInteraction) -> Unit = { userInteraction ->
    when (userInteraction) {
      is RangePracticeListInteraction.OnStartPracticing -> navigation.goToFilterRangePractice()
    }
  }

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Error -> NoResultsUi(
      message = stringResource(id = R.string.something_went_wrong),
      interact = eventConsumer,
    )
    is ComponentState.Success -> TrainingAnswerUi(
      items = state.result,
      interact = eventConsumer,
    )
  }
}

@Composable
internal fun TrainingAnswerUi(
  items: List<RangePracticeResult>,
  interact: (RangePracticeListInteraction) -> Unit = {},
) {
  // Draws empty list text if there is no transactions
  if (items.isEmpty()) {
    NoResultsUi(
      message = stringResource(id = R.string.no_practice),
      interact = interact,
    )
    return
  }

  // Draws transactions items.
  TrainingAnswerList(items = items, interact = interact)
}