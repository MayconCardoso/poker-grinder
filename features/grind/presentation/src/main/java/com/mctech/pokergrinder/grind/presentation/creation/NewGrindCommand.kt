package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class NewGrindCommand : ViewCommand {
  object CloseScreen : NewGrindCommand()
}