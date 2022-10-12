package com.mctech.pokergrinder.grind.presentation.grind_gameplay_register

import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.localization.R

internal data class RegisterFlipState(
  val message: Int = R.string.select_the_tournament,
  val currentFlow: RegisterFlipFlow = RegisterFlipFlow.LOADING,
  val tournaments: List<SessionTournament> = listOf(),
  val disableCards: List<Card> = listOf(),
)

internal enum class RegisterFlipFlow {
  LOADING,
  TOURNAMENT_PICKER,
  HERO_CARD_PICKER,
  VILLAIN_CARD_PICKER,
  BOARD_PICKER,
  WHO_WON,
  ;

  fun isOneOf(vararg flow: RegisterFlipFlow) = flow.contains(this)

  fun isNotOneOf(vararg flow: RegisterFlipFlow) = !isOneOf(*flow.toList().toTypedArray())
}
