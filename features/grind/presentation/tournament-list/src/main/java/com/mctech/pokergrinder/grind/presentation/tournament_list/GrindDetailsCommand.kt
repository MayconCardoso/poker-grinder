package com.mctech.pokergrinder.grind.presentation.tournament_list

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

internal sealed class GrindDetailsCommand : ViewCommand {
  object CloseScreen : GrindDetailsCommand()

  data class ShowTournamentSelection(
    val options: List<SessionTournament>,
    ) : GrindDetailsCommand()

  data class GoToTournamentEditor(
    val session: Session,
    val sessionTournament: SessionTournament?,
  ) : GrindDetailsCommand()

  object InsufficientBalanceError : GrindDetailsCommand()
}