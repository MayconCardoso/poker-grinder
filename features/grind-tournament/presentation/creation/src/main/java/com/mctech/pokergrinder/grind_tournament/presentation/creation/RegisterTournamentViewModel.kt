package com.mctech.pokergrinder.grind_tournament.presentation.creation

import androidx.annotation.VisibleForTesting
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentAnalytics
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.UpdatesTournamentUseCase
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.entities.TournamentType
import com.mctech.pokergrinder.tournament.domain.usecase.LoadTournamentUseCase
import com.mctech.pokergrinder.tournament.domain.usecase.SavesTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
internal class RegisterTournamentViewModel @Inject constructor(
  // Analytics
  private val analytics: GrindTournamentAnalytics,

  // Session tournaments.
  private val updatesTournamentUseCase: UpdatesTournamentUseCase,
  private val registerTournamentUseCase: RegisterTournamentUseCase,

  // Tournaments
  private val loadTournamentUseCase: LoadTournamentUseCase,
  private val savesTournamentUseCase: SavesTournamentUseCase,
) : BaseViewModel() {

  /**
   * Holds the grind session
   */
  @VisibleForTesting
  var session: Session? = null

  /**
   * Holds the component current rendered state.
   */
  private val _componentState by lazy { MutableStateFlow<SessionTournament?>(null) }
  val componentState: StateFlow<SessionTournament?> by lazy { _componentState }

  @OnInteraction(RegisterTournamentInteraction.ScreenFirstOpen::class)
  private fun onScreenOpened(interaction: RegisterTournamentInteraction.ScreenFirstOpen) {
    // Holds session
    this.session = interaction.session

    // Updates screen.
    _componentState.value = interaction.tournament
  }

  @OnInteraction(RegisterTournamentInteraction.SaveTournament::class)
  private suspend fun saveTournamentInteraction(
    interaction: RegisterTournamentInteraction.SaveTournament,
  ) {
    // Handles tournament registration.
    handleSessionTournamentRegistration(interaction)

    // Handles tournament creation.
    handlesTournamentCreation(interaction.buyIn, interaction.title)

    // Closes screen
    sendCommand(RegisterTournamentCommand.CloseScreen)
  }

  private suspend fun handleSessionTournamentRegistration(
    interaction: RegisterTournamentInteraction.SaveTournament,
  ) {
    // Doesn't do anything without a session
    val session = this.session ?: return

    // Checks if session tournament exists
    val tournament = _componentState.value

    // Updates if already exists
    if (tournament != null) {
      updatesTournamentUseCase(
        tournament.copy(
          title = interaction.title,
          buyIn = interaction.buyIn,
          profit = interaction.profit + interaction.addNewProfit,
        )
      )
    }

    // Tournament does not exist.
    else {
      analytics.onTourneyRegistered(title = interaction.title, buyIn = interaction.buyIn)
      registerTournamentUseCase(
        session = session,
        title = interaction.title,
        buyIn = interaction.buyIn,
      )
    }
  }

  private suspend fun handlesTournamentCreation(buyIn: Double, title: String) {
    // Tournament already exists, so nothing had to be done.
    if (loadTournamentUseCase(title) != null) {
      return
    }

    // Creates new tournament.
    savesTournamentUseCase(
      tournament = Tournament(
        id = "",
        isBounty = false,
        guaranteed = 0,
        countReBuy = 0,
        buyIn = buyIn.toFloat(),
        title = title,
        type = TournamentType.REGULAR,
        startTimeInMs = 10.hours.inWholeMilliseconds
      )
    )
  }

}