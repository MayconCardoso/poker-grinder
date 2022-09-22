package com.mctech.pokergrinder.grind.presentation.grind_details

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class GrindDetailsCommand : ViewCommand {
  object CloseScreen : GrindDetailsCommand()

  data class GoToTournamentEditor(
    val session: Session,
    val sessionTournament: SessionTournament?,
  ) : GrindDetailsCommand()
}