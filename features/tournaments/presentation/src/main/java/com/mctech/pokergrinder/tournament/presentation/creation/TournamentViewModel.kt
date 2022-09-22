package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.domain.entities.TournamentType
import com.mctech.pokergrinder.tournaments.domain.usecase.SavesTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
internal class TournamentViewModel @Inject constructor(
  private val savesTournamentUseCase: SavesTournamentUseCase,
) : BaseViewModel() {

  private val _componentState by lazy { MutableStateFlow<Tournament?>(null) }
  val componentState: StateFlow<Tournament?> by lazy { _componentState }

  @OnInteraction(TournamentInteraction.ScreenFirstOpen::class)
  private fun onScreenOpened(interaction: TournamentInteraction.ScreenFirstOpen) {
    _componentState.value = interaction.tournament
  }

  @OnInteraction(TournamentInteraction.SaveTournament::class)
  private suspend fun saveTournamentInteraction(interaction: TournamentInteraction.SaveTournament) {
    // Creates tournament
    val tournament = Tournament(
      id = _componentState.value?.id ?: "",
      isBounty = false,
      buyIn = interaction.buyIn,
      title = interaction.title,
      type = TournamentType.REGULAR,
      guaranteed = interaction.guaranteed,
      countReBuy = interaction.countBuyIn,
      startTimeInMs = 10.hours.inWholeMilliseconds
    )

    // Saves tournament
    savesTournamentUseCase(tournament)

    // Closes screen
    sendCommand(TournamentCommand.CloseScreen)
  }

}