package com.mctech.pokergrinder.summary.presentation.tournaments

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrind.threading.CoroutineDispatchers
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.domain.usecases.ObserveTournamentSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class SummaryTournamentsViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeTournamentSummaryUseCase: ObserveTournamentSummaryUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the current shown tournaments that user can select.
   */
  private var shownTournaments = mutableListOf<TournamentSummary>()

  /**
   * Holds the current filter query that is being used.
   */
  private var currentFilterQuery: String = ""

  /**
   * Holds the rendered state.
   */
  private val _state by lazy {
    MutableStateFlow<ComponentState<List<TournamentSummary>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<TournamentSummary>>> by lazy { _state }

  // endregion

  // region Interactions

  override suspend fun initializeComponents() {
    viewModelScope.async { observeInvestmentSummary() }
  }

  private fun observeInvestmentSummary() {
    observeTournamentSummaryUseCase()
      .onEach { tournaments ->
        // Keep shown tournaments.
        this.shownTournaments.clear()
        this.shownTournaments.addAll(tournaments)

        // Shows tournaments.
        updateTournamentListBasedOnCurrentQuery()
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(SummaryTournamentListInteraction.NewFilterQuery::class)
  private suspend fun onFilterQueryChanged(
    interaction: SummaryTournamentListInteraction.NewFilterQuery,
  ) = withContext(dispatchers.default) {
    // Holds the current query
    currentFilterQuery = interaction.text

    // Update tournament list based on query.
    updateTournamentListBasedOnCurrentQuery()
  }

  // endregion

  // region Secondary functions

  private suspend fun updateTournamentListBasedOnCurrentQuery() = withContext(dispatchers.default) {
    if (shownTournaments.isEmpty()) return@withContext

    // Filter tournaments
    val tournaments = if (currentFilterQuery.isBlank()) {
      shownTournaments
    } else {
      shownTournaments.filter { it.title.contains(currentFilterQuery, true) }
    }

    // Shows tournaments.
    withContext(dispatchers.main) {
      _state.value = ComponentState.Success(tournaments)
    }
  }

  // endregion

}