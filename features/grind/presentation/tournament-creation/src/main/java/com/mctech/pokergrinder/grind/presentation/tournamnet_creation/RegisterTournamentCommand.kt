package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class RegisterTournamentCommand : ViewCommand {
  object CloseScreen : RegisterTournamentCommand()
  object InsufficientBalanceError : RegisterTournamentCommand()
}