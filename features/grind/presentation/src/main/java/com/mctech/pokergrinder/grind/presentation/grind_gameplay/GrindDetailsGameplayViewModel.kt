package com.mctech.pokergrinder.grind.presentation.grind_gameplay

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentFlipsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindDetailsGameplayViewModel @Inject constructor(
  private val observeGrindTournamentFlipsUseCase: ObserveGrindTournamentFlipsUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the opened session.
   */
  lateinit var session: Session

  /**
   * Holds the tournament flips state.
   */
  private val _state by lazy {
    MutableStateFlow<ComponentState<List<SessionTournamentFlip>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<SessionTournamentFlip>>> by lazy { _state }

  // endregion

  // region Interactions

  @OnInteraction(GrindDetailsGameplayInteraction.ScreenFirstOpen::class)
  private fun onScreenFirstOpen(interaction: GrindDetailsGameplayInteraction.ScreenFirstOpen) {
    this.session = interaction.session

    viewModelScope.async { observeTournamentsFlip(interaction.session) }
  }

  // endregion

  // region Session Actions

  private fun observeTournamentsFlip(session: Session) {
    // Observe tournaments flips
    observeGrindTournamentFlipsUseCase(session.id)
      .onEach { flips ->
        _state.value = ComponentState.Success(flips)
      }
      .launchIn(viewModelScope)
  }

  // endregion
}