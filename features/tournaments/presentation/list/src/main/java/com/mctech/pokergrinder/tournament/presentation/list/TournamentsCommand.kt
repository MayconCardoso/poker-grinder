package com.mctech.pokergrinder.tournament.presentation.list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class TournamentsCommand : ViewCommand {
  data class NavigateToEditor(val tournament: Tournament) : TournamentsCommand()
}