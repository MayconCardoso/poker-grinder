package com.mctech.pokergrinder.grind_gameplay.domain.entities

/**
 * Declares a grind session flip instance.
 *
 * @property id UUID session id.
 * @property idSession UUID session tournament id.
 * @property tournament tournament title.
 * @property heroHand hero cards.
 * @property villainHand villain cards.
 * @property board cards on board.
 * @property won indicates if here has won the flip.
 */
data class SessionTournamentFlip(
  val id: String,
  val idSession: String,
  val tournament: String,
  val heroHand: String,
  val villainHand: String,
  val board: String,
  val won: Boolean,
)