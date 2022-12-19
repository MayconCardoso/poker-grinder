package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.ViewCommand

/**
 * Holds the available command for the feature
 */
internal sealed class NewGrindCommand : ViewCommand {

  /**
   * Used to close the screen and navigate back.
   */
  object CloseScreen : NewGrindCommand()
}