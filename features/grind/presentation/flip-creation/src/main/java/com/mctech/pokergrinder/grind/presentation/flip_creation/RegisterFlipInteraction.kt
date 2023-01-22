package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament

/**
 * Holds the available interactions for the feature.
 */
internal sealed class RegisterFlipInteraction : UserInteraction {

  /**
   * Used to initialize the screen.
   */
  data class ScreenFirstOpen(val session: Session) : RegisterFlipInteraction()

  /**
   * Used to indicate a [tournament] has been selected.
   */
  data class TournamentSelected(val tournament: SessionTournament) : RegisterFlipInteraction()

  /**
   * Used to indicate a [card] has been selected.
   */
  data class CardSelected(val card: Card) : RegisterFlipInteraction()

  /**
   * Used to indicate a [card] has been unselected.
   */
  data class CardUnselected(val card: Card) : RegisterFlipInteraction()

  /**
   * Used to indicate the back pressed intention has been requested.
   */
  object OnBackPressed : RegisterFlipInteraction()

  /**
   * Used to indicate user has won the flip.
   */
  object HeroWonFlip : RegisterFlipInteraction()

  /**
   * Used to villain user has won the flip.
   */
  object VillainWonFlip : RegisterFlipInteraction()
}