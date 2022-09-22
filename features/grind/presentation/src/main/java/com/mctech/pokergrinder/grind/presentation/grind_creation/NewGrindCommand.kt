package com.mctech.pokergrinder.grind.presentation.grind_creation

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class NewGrindCommand : ViewCommand {
  object CloseScreen : NewGrindCommand()
}