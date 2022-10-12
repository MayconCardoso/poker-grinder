package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class RegisterFlipCommand : ViewCommand {
  object CloseScreen : RegisterFlipCommand()
}