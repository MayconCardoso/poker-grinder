package com.mctech.pokergrinder.grind.presentation.navigation

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Used to navigate through grind feature components.
 */
interface GrindNavigation {

  /**
   * Goes to new session screen.
   */
  fun goToNewSession()

  /**
   * Goes to settings screen.
   */
  fun goToSettings()

  /**
   * Goes to session details screen.
   */
  fun goToSessionDetails(session: Session)

  /**
   * Goes to session tournament screen.
   */
  fun goToSessionTournament(session: Session, sessionTournament: SessionTournament?)

  /**
   * Goes to session tournament gameplay screen.
   */
  fun goToSessionTournamentGameplay(session: Session)

  /**
   * Navigates back.
   */
  fun navigateBack()
}