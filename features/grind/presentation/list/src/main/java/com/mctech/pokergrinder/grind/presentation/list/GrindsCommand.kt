package com.mctech.pokergrinder.grind.presentation.list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.grind.domain.entities.Session

/**
 * Holds the available command for the feature
 */
internal sealed class GrindsCommand : ViewCommand {

  /**
   * Called to navigate to the session editor.
   */
  data class NavigateToEditor(val session: Session) : GrindsCommand()
}