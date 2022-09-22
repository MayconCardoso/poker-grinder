package com.mctech.pokergrinder.grind.presentation.grind_list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import com.mctech.pokergrinder.grind.presentation.grind_list.adapter.GrindAdapterConsumerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindsViewModel @Inject constructor(
  private val observeAllGrindsUseCase: ObserveAllGrindsUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Session>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<Session>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeAllGrindsUseCase()
      .onEach { sessions ->
        _componentState.value = ComponentState.Success(sessions)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(GrindsInteraction.OnGrindEvent::class)
  private suspend fun onTournamentEventInteraction(interaction: GrindsInteraction.OnGrindEvent) {
    when (interaction.event) {
      is GrindAdapterConsumerEvent.SessionClicked -> {
        sendCommand(GrindsCommand.NavigateToEditor(interaction.event.session))
      }
    }
  }

}