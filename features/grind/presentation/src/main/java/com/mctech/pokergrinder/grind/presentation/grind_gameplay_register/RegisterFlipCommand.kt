package com.mctech.pokergrinder.grind.presentation.grind_gameplay_register

import com.mctech.pokergrinder.architecture.ViewCommand

internal sealed class RegisterFlipCommand : ViewCommand {
  object CloseScreen : RegisterFlipCommand()
}