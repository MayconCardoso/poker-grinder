package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class RegisterFlipCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : RegisterFlipCommand()
}