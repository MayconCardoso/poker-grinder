package com.mctech.pokergrinder.grind_tournament.presentation.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentAnalytics
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.domain.usecase.GroupGrindTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class GrindDetailsViewModel @Inject constructor(
  private val analytics: GrindTournamentAnalytics,
  private val observeSettingsUseCase: ObserveSettingsUseCase,
  private val registerTournamentUseCase: RegisterTournamentUseCase,
  private val groupGrindTournamentUseCase: GroupGrindTournamentUseCase,
  private val observeGrindTournamentUseCase: ObserveGrindTournamentUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the opened session.
   */
  @VisibleForTesting
  var session: Session? = null

  /**
   * Holds the original tournament list.
   */
  @VisibleForTesting
  var originalTemplateList = listOf<SessionTournament>()

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
    // Holds session to future usage.
    this.session = interaction.session

    // Observe changes.
    observeTournaments(interaction.session)
  }

  @OnInteraction(GrindDetailsInteraction.RegisterTournamentClicked::class)
  private suspend fun registerTournamentClicked() {
    analytics.onNewTourneyClicked()
    openTournament(tournament = null)
  }

  @OnInteraction(GrindDetailsInteraction.OnTournamentEvent::class)
  private suspend fun onTournamentEvent(interaction: GrindDetailsInteraction.OnTournamentEvent) {
    when (interaction.event) {
      is GrindDetailsConsumerEvent.TournamentClicked -> {
        analytics.onTourneyClicked(interaction.event.tournament)
        openTournament(tournament = interaction.event.tournament)
      }
      is GrindDetailsConsumerEvent.DuplicateClicked -> {
        analytics.onTourneyDuplicated(interaction.event.tournament)
        duplicateTournament(tournament = interaction.event.tournament)
      }
    }
  }

  private suspend fun duplicateTournament(tournament: SessionTournament) {
    // Register new item.
    registerTournamentUseCase(
      session = requireNotNull(session),
      title = tournament.title,
      buyIn = originalTemplateList.first { it.title == tournament.title }.buyIn
    )
  }

  // endregion

  // region Session Actions

  private fun observeTournaments(session: Session) {
    // Observe tournaments
    observeGrindTournamentUseCase(session.id)
      // Observe settings
      .combine(observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)) { tournaments, settings ->
        // Holds the original list for future usage.
        this.originalTemplateList = tournaments

        // Should group same same tournament
        if (settings?.asBoolean() == true) {
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
      session = requireNotNull(session),
      sessionTournament = tournament,
    )

    // Open register tournament
    sendCommand(command)
  }

  // endregion
}