package com.mctech.pokergrinder.tournament.presentation.list_component

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrind.threading.CoroutineDispatchers
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.domain.usecase.ObserveTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
public class TournamentListViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeTournamentUseCase: ObserveTournamentUseCase,
) : BaseViewModel() {

  /**
   * Holds the current shown tournaments that user can select.
   */
  private var shownTournaments = mutableListOf<Tournament>()

  /**
   * Holds the current filter query that is being used.
   */
  private var currentFilterQuery: String = ""

  /**
   * Holds the rendered state.
   */
  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Tournament>>>(ComponentState.Loading.FromEmpty)
  }
  public val componentState: StateFlow<ComponentState<List<Tournament>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeTournamentUseCase()
      .onEach { tournaments ->
        // Keep shown tournaments.
        this.shownTournaments.clear()
        this.shownTournaments.addAll(tournaments)

        // Shows tournaments.
        updateTournamentListBasedOnCurrentQuery()
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(TournamentListInteraction.NewFilterQuery::class)
  private suspend fun onFilterQueryChanged(
    interaction: TournamentListInteraction.NewFilterQuery,
  ) = withContext(dispatchers.default) {
    // Holds the current query
    currentFilterQuery = interaction.text

    // Update tournament list based on query.
    updateTournamentListBasedOnCurrentQuery()
  }

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
      _componentState.value = ComponentState.Success(tournaments)
    }
  }

}