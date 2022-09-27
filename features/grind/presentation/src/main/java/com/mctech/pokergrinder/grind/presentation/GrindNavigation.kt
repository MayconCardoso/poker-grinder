package com.mctech.pokergrinder.grind.presentation

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament

public interface GrindNavigation {
  public fun goToNewSession()
  public fun goToSessionDetails(session: Session)
  public fun goToSessionTournament(session: Session, sessionTournament: SessionTournament?)
  public fun navigateBack()
}