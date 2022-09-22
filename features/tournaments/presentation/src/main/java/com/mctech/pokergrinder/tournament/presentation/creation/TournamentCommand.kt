package com.mctech.pokergrinder.tournament.presentation.creation

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class TournamentCommand : ViewCommand {
  object CloseScreen : TournamentCommand()
}