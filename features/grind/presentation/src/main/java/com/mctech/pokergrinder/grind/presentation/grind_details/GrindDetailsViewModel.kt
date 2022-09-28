package com.mctech.pokergrinder.grind.presentation.grind_details

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.usecase.GroupGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindUseCase
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumerEvent
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindDetailsViewModel @Inject constructor(
  private val observeGrindUseCase: ObserveGrindUseCase,
  private val observeSettingsUseCase: ObserveSettingsUseCase,
  private val observeGrindTournamentUseCase: ObserveGrindTournamentUseCase,
  private val groupGrindTournamentUseCase: GroupGrindTournamentUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the original tournament list.
   */
  private var originalTemplateList = listOf<SessionTournament>()

  /**
   * Holds the session summary state.
   */
  private val _detailsState by lazy {
    MutableStateFlow<ComponentState<Session>>(ComponentState.Loading.FromEmpty)
  }
  val detailsState: StateFlow<ComponentState<Session>> by lazy { _detailsState }

  /**
   * Holds the session registered tournaments.
   */
  private val _tournamentsState by lazy {
    MutableStateFlow<ComponentState<List<SessionTournament>>>(ComponentState.Loading.FromEmpty)
  }
  val tournamentsState: StateFlow<ComponentState<List<SessionTournament>>> by lazy { _tournamentsState }

  // endregion

  // region Interactions

  @OnInteraction(GrindDetailsInteraction.ScreenFirstOpen::class)
  private fun onScreenFirstOpen(interaction: GrindDetailsInteraction.ScreenFirstOpen) {
    viewModelScope.async { observeDetails(interaction.session) }
    viewModelScope.async { observeTournaments(interaction.session) }
  }

  @OnInteraction(GrindDetailsInteraction.RegisterTournamentClicked::class)
  private suspend fun registerTournamentClicked() {
    openTournament(tournament = null)
  }

  @OnInteraction(GrindDetailsInteraction.OnTournamentEvent::class)
  private suspend fun onTournamentEvent(interaction: GrindDetailsInteraction.OnTournamentEvent) {
    when (interaction.event) {
      is GrindDetailsConsumerEvent.TournamentClicked -> {
        openTournament(tournament = interaction.event.tournament)
      }
    }
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
    // Observe tournaments
    observeGrindTournamentUseCase(session.id)
      // Observe settings
      .combine(observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)) { tournaments, settings ->
        // Holds the original list for future usage.
        this.originalTemplateList = tournaments

        // Should group same same tournament
        if (settings.asBoolean()) {
          groupGrindTournamentUseCase(tournaments)
        }

        // Should not group tournaments.
        else {
          tournaments
        }
      }
      // Handle new state
      .onEach { tournaments ->
        _tournamentsState.value = ComponentState.Success(tournaments)
      }
      .launchIn(viewModelScope)
  }

  private suspend fun openTournament(tournament: SessionTournament?) {
    // Gets state.
    val state = _detailsState.value as? ComponentState.Success ?: return

    // Checks if tournament is grouped, if so, ask user to pick which tournament they wanna change.
    if (tournament?.isGrouped == true) {
      // Filter tournaments.
      val tournamentOptions = originalTemplateList.filter { it.title == tournament.title }

      // Request selection picker
      sendCommand(GrindDetailsCommand.ShowTournamentSelection(tournamentOptions))
      return
    }

    // Gets command
    val command = GrindDetailsCommand.GoToTournamentEditor(
      session = state.result,
      sessionTournament = tournament,
    )

    // Open register tournament
    sendCommand(command)
  }
}