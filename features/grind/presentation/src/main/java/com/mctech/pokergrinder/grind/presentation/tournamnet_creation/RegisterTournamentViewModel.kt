package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.UpdatesTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class RegisterTournamentViewModel @Inject constructor(
  private val updatesTournamentUseCase: UpdatesTournamentUseCase,
  private val registerTournamentUseCase: RegisterTournamentUseCase,
) : BaseViewModel() {

  /**
   * Holds the grind session
   */
  private lateinit var session: Session

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
    // Checks if session tournament exists
    val tournament = _componentState.value

    try {

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
        registerTournamentUseCase(
          session = session,
          title = interaction.title,
          buyIn = interaction.buyIn,
        )
      }

      // Closes screen
      sendCommand(RegisterTournamentCommand.CloseScreen)
    } catch (exception: Exception) {
      if(exception is BankrollException.InsufficientBalance) {
        sendCommand(RegisterTournamentCommand.InsufficientBalanceError)
      }
    }
  }

}