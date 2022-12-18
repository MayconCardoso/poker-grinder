package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.entities.TournamentType
import com.mctech.pokergrinder.tournament.domain.usecase.SavesTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

@HiltViewModel
internal class NewTournamentViewModel @Inject constructor(
  private val savesTournamentUseCase: SavesTournamentUseCase,
) : BaseViewModel() {

  private val _componentState by lazy { MutableStateFlow<Tournament?>(null) }
  val componentState: StateFlow<Tournament?> by lazy { _componentState }

  @OnInteraction(NewTournamentInteraction.ScreenFirstOpen::class)
  private fun onScreenOpened(interaction: NewTournamentInteraction.ScreenFirstOpen) {
    _componentState.value = interaction.tournament
  }

  @OnInteraction(NewTournamentInteraction.SaveTournament::class)
  private suspend fun saveTournamentInteraction(interaction: NewTournamentInteraction.SaveTournament) {
    // Creates tournament
    val tournament = Tournament(
      id = _componentState.value?.id.orEmpty(),
      isBounty = false,
      guaranteed = 0,
      countReBuy = 0,
      buyIn = interaction.buyIn,
      title = interaction.title,
      type = TournamentType.REGULAR,
      startTimeInMs = 10.hours.inWholeMilliseconds
    )

    // Saves tournament
    savesTournamentUseCase(tournament)

    // Closes screen
    sendCommand(NewTournamentCommand.CloseScreen)
  }

}