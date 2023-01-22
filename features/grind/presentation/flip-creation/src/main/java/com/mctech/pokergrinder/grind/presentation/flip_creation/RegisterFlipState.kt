package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.localization.R

/**
 * Represents a flip registration state.
 * @property message message shown.
 * @property currentFlow where the user are in the creation flow.
 * @property tournaments tournaments played in the session.
 * @property disableCards all cards that cannot be selected.
 */
internal data class RegisterFlipState(
  val message: Int = R.string.select_the_tournament,
  val currentFlow: RegisterFlipFlow = RegisterFlipFlow.LOADING,
  val tournaments: List<SessionTournament> = listOf(),
  val disableCards: List<Card> = listOf(),
)

/**
 * Describe all parts of the registration flow.
 */
internal enum class RegisterFlipFlow {
  /**
   * Component is being loaded.
   */
  LOADING,

  /**
   * User needs to select a tournament.
   */
  TOURNAMENT_PICKER,

  /**
   * User needs to select hero cards.
   */
  HERO_CARD_PICKER,

  /**
   * User needs to select villain cards.
   */
  VILLAIN_CARD_PICKER,

  /**
   * User needs to select board cards.
   */
  BOARD_PICKER,

  /**
   * User needs to indicate who have won the flip.
   */
  WHO_WON,
  ;

  fun isOneOf(vararg flow: RegisterFlipFlow) = flow.contains(this)

  fun isNotOneOf(vararg flow: RegisterFlipFlow) = !isOneOf(*flow.toList().toTypedArray())
}
