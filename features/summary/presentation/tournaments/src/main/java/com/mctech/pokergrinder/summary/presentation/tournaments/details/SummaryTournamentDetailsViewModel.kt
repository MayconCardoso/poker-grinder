package com.mctech.pokergrinder.summary.presentation.tournaments.details

import androidx.lifecycle.viewModelScope
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.summary.domain.usecases.ObserveTournamentSummaryDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class SummaryTournamentDetailsViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeTournamentSummaryUseCase: ObserveTournamentSummaryDetailsUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the rendered state.
   */
  private val _tournamentState by lazy {
    MutableStateFlow<ComponentState<SummaryTournamentDetailsState>>(ComponentState.Loading.FromEmpty)
  }
  val tournamentState: StateFlow<ComponentState<SummaryTournamentDetailsState>> by lazy { _tournamentState }

  /**
   * Holds the rendered chart state.
   */
  private val _performanceState by lazy {
    MutableStateFlow<List<MoneyVariationEntry>>(listOf())
  }
  val performanceState: StateFlow<List<MoneyVariationEntry>> by lazy { _performanceState }

  // endregion

  // region Interactions

  @OnInteraction(SummaryTournamentDetailsInteraction.OnScreenFirstOpened::class)
  private suspend fun onScreenFirstOpenInteraction(
    interaction: SummaryTournamentDetailsInteraction.OnScreenFirstOpened,
  ) = withContext(dispatchers.default) {
    observeTournamentSummaryUseCase(interaction.tournamentSummary)
      .onEach { tournaments ->
        // Create chart
        var entryBalance = 0.0
        val chartItemState = tournaments.reversed().map { tournament ->
          entryBalance += tournament.profit
          MoneyVariationEntry(entryBalance)
        }

        // Create tournament states
        val tournamentState =  SummaryTournamentDetailsState(
          tournaments = tournaments,
          groupedSummary = interaction.tournamentSummary,
        )

        // Shows data.
        withContext(dispatchers.main) {

          // Set tournament details
          _tournamentState.value = ComponentState.Success(tournamentState)

          // Set chart details
          _performanceState.value = chartItemState
        }
      }
      .launchIn(viewModelScope)
  }

  // endregion


}