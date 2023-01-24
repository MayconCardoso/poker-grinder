package com.mctech.pokergrinder.ranges.presentation.viewer

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.ranges.domain.entities.EmptyRange
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveRangeUseCase
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RangeViewerViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeRangeUseCase: ObserveRangeUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<Range>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<Range>> by lazy { _componentState }

  @OnInteraction(RangeViewerInteraction.OnScreenFirstOpened::class)
  private fun onScreenFirstOpenInteraction(
    interaction: RangeViewerInteraction.OnScreenFirstOpened,
  ) {
    observeRangeUseCase(interaction.range)
      .onEach { range ->
        val updatedRange = resolveEmptyRangeSlots(range)
        withContext(dispatchers.main) {
          _componentState.value = ComponentState.Success(updatedRange)
        }
      }
      .launchIn(viewModelScope)
  }

  private suspend fun resolveEmptyRangeSlots(range: Range) = withContext(dispatchers.default){
    val positionTasks = range.handPosition.map { position ->
      viewModelScope.async {
        // Filled inputs
        val filledInputs = position.hands.associateBy { it.hand.formattedName() }

        // Create the entire range
        val inputs = EmptyRange.rangeHands.map { emptyHand ->
          if (filledInputs.containsKey(emptyHand.formattedName())) {
            filledInputs[emptyHand.formattedName()]
          } else {
            RangeHandInput(
              hand = emptyHand,
              filledColor = "#FFFFFF"
            )
          }
        }

        position.copy(
          hands = inputs.filterNotNull()
        )
      }
    }

    range.copy(handPosition = positionTasks.awaitAll())
  }

}