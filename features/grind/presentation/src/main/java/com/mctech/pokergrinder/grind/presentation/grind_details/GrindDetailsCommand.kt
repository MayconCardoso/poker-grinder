package com.mctech.pokergrinder.grind.presentation.grind_details

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class GrindDetailsCommand : ViewCommand {
  object CloseScreen : GrindDetailsCommand()
}