package com.mctech.pokergrinder.grind.presentation.grind_list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class GrindsCommand : ViewCommand {
  data class NavigateToEditor(val session: Session) : GrindsCommand()
}