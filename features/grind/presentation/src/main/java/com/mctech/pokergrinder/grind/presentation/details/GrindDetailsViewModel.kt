package com.mctech.pokergrinder.grind.presentation.details

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindDetailsViewModel @Inject constructor(
  private val observeGrindUseCase: ObserveGrindUseCase,
  private val observeGrindTournamentUseCase: ObserveGrindTournamentUseCase,
) : BaseViewModel() {

  private val _detailsState by lazy {
    MutableStateFlow<ComponentState<Session>>(ComponentState.Loading.FromEmpty)
  }
  val detailsState: StateFlow<ComponentState<Session>> by lazy { _detailsState }

  private val _tournamentsState by lazy {
    MutableStateFlow<ComponentState<List<SessionTournament>>>(ComponentState.Loading.FromEmpty)
  }
  val tournamentsState: StateFlow<ComponentState<List<SessionTournament>>> by lazy { _tournamentsState }

  @OnInteraction(GrindDetailsInteraction.ScreenFirstOpen::class)
  private fun onScreenFirstOpen(interaction: GrindDetailsInteraction.ScreenFirstOpen) {
    viewModelScope.async { observeDetails(interaction.session) }
    viewModelScope.async { observeTournaments(interaction.session) }
  }

  private fun observeDetails(session: Session) {
    observeGrindUseCase(session.id)
      .onEach { state ->
        _detailsState.value = ComponentState.Success(state)
      }
      .launchIn(viewModelScope)
  }

  private fun observeTournaments(session: Session) {
    observeGrindTournamentUseCase(session.id)
      .onEach { tournaments ->
        _tournamentsState.value = ComponentState.Success(tournaments)
      }
      .launchIn(viewModelScope)
  }
}