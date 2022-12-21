package com.mctech.pokergrinder.grind.testing

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip

/**
 * Creates a new Session for test purpose.
 */
fun newSession(
  id: String = "0",
  cash: Double = 0.0,
  buyIn: Double = 0.0,
  avgBuyIn: Double = 0.0,
  title: String = "",
  isOpened: Boolean = false,
  startTimeInMs: Long = 0,
  tournamentsPlayed: Int = 0,
) = Session(
  id = id,
  cash = cash,
  buyIn = buyIn,
  avgBuyIn = avgBuyIn,
  title = title,
  isOpened = isOpened,
  startTimeInMs = startTimeInMs,
  tournamentsPlayed = tournamentsPlayed,
)

/**
 * Creates a new Session tournament for test purpose.
 */
fun newTournament(
  id: String = "0",
  idSession: String = "0",
  idTransactionProfit: String? = "0",
  idTransactionBuyIn: String = "0",
  title: String = "",
  buyIn: Double = 0.0,
  profit: Double = 0.0,
  isGrouped: Boolean = false,
  startTimeInMs: Long = 0,
) = SessionTournament(
  id = id,
  idSession = idSession,
  idTransactionProfit = idTransactionProfit,
  idTransactionBuyIn = idTransactionBuyIn,
  title = title,
  buyIn = buyIn,
  profit = profit,
  isGrouped = isGrouped,
  startTimeInMs = startTimeInMs,
)

/**
 * Creates a new Session tournament flip for test purpose.
 */
fun newSessionFlip(
  id: String = "0",
  won: Boolean = false,
  idSession: String = "0",
  tournament: String = "",
  board: String = "",
  heroHand: String = "",
  villainHand: String = "",
) = SessionTournamentFlip(
  id = id,
  won = won,
  idSession = idSession,
  tournament = tournament,
  board = board,
  heroHand = heroHand,
  villainHand = villainHand,
)