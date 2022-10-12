package com.mctech.pokergrinder.grind.presentation.navigation

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

public interface GrindNavigation {
  public fun goToNewSession()
  public fun goToSettings()
  public fun goToSessionDetails(session: Session)
  public fun goToSessionTournament(session: Session, sessionTournament: SessionTournament?)
  public fun goToSessionTournamentGameplay(session: Session)
  public fun navigateBack()
}