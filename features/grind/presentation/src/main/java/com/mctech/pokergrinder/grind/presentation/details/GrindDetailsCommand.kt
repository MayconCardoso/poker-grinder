package com.mctech.pokergrinder.grind.presentation.details

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class GrindDetailsCommand : ViewCommand {
  object CloseScreen : GrindDetailsCommand()
}