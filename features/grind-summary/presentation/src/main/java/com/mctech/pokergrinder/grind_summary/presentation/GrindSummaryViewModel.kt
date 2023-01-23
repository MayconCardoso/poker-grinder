package com.mctech.pokergrinder.grind_summary.presentation

import androidx.lifecycle.viewModelScope
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindUseCase
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind_gameplay.domain.usecase.ObserveGrindTournamentFlipsUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.ObserveGrindTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindSummaryViewModel @Inject constructor(
  private val observeGrindUseCase: ObserveGrindUseCase,
  private val observeGrindTournamentUseCase: ObserveGrindTournamentUseCase,
  private val observeGrindTournamentFlipsUseCase: ObserveGrindTournamentFlipsUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the session summary state.
   */
  private val _detailsState by lazy {
    MutableStateFlow<ComponentState<Session>>(ComponentState.Loading.FromEmpty)
  }
  val detailsState: StateFlow<ComponentState<Session>> by lazy { _detailsState }

  /**
   * Holds the session chart.
   */
  private val _chartState by lazy { MutableStateFlow<List<MoneyVariationEntry>>(listOf()) }
  val chartState: StateFlow<List<MoneyVariationEntry>> by lazy { _chartState }

  /**
   * Holds the tournament flips state.
   */
  private val _flipState by lazy { MutableStateFlow<List<SessionTournamentFlip>>(listOf()) }
  val flipState: StateFlow<List<SessionTournamentFlip>> by lazy { _flipState }

  // endregion

  // region Interactions

  @OnInteraction(GrindSummaryInteraction.ScreenFirstOpen::class)
  private fun onScreenFirstOpen(interaction: GrindSummaryInteraction.ScreenFirstOpen) {
    viewModelScope.async { observeDetails(interaction.session) }
    viewModelScope.async { observeTournaments(interaction.session) }
    viewModelScope.async { observeTournamentsFlip(interaction.session) }
  }

  // endregion

  // region Session Actions

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
        var balance = 0.0
        _chartState.value = tournaments.reversed().map {
          balance += it.computesBalance()
          MoneyVariationEntry(balance)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun observeTournamentsFlip(session: Session) {
    // Observe tournaments flips
    observeGrindTournamentFlipsUseCase(session.id)
      .onEach { flips ->
        _flipState.value = flips
      }
      .launchIn(viewModelScope)
  }
  // endregion

}