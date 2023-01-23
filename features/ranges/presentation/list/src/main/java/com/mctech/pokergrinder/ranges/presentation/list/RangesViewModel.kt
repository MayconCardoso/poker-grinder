package com.mctech.pokergrinder.ranges.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveAllRangesUseCase
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangeAdapterConsumerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class RangesViewModel @Inject constructor(
  private val observeAllRangesUseCase: ObserveAllRangesUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Range>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<Range>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeAllRangesUseCase()
      .onEach { sessions ->
        _componentState.value = ComponentState.Success(sessions)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(RangesInteraction.OnRangeEvent::class)
  private suspend fun onTournamentEventInteraction(interaction: RangesInteraction.OnRangeEvent) {
    when (interaction.event) {
      is RangeAdapterConsumerEvent.Clicked -> {
        sendCommand(RangesCommand.NavigateToViewer(interaction.event.range))
      }
    }
  }

}