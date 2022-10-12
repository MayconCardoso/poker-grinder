package com.mctech.pokergrinder.tournament.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.tournaments.domain.usecase.ComputesAverageBuyInUseCase
import com.mctech.pokergrinder.tournaments.domain.usecase.ComputesInvestmentPerSessionUseCase
import com.mctech.pokergrinder.tournaments.domain.usecase.ObserveTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class TournamentsViewModel @Inject constructor(
  private val observeTournamentUseCase: ObserveTournamentUseCase,
  private val computesAverageBuyInUseCase: ComputesAverageBuyInUseCase,
  private val computesInvestmentPerSessionUseCase: ComputesInvestmentPerSessionUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<TournamentsState>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<TournamentsState>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeTournamentUseCase()
      .onEach { tournaments ->
        // Creates a new tournament state
        val state = TournamentsState(
          tournaments = tournaments,
          averageBuyIn = computesAverageBuyInUseCase(tournaments),
          investmentPerSession = computesInvestmentPerSessionUseCase(tournaments),
        )

        // Emits state.
        _componentState.value = ComponentState.Success(state)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(TournamentsInteraction.OnTournamentClicked::class)
  private suspend fun onTournamentEventInteraction(
    interaction: TournamentsInteraction.OnTournamentClicked,
  ) {
    sendCommand(TournamentsCommand.NavigateToEditor(interaction.tournament))
  }

}