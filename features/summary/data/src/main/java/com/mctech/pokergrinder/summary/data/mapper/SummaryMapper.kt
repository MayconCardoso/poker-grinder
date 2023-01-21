package com.mctech.pokergrinder.summary.data.mapper

import com.mctech.pokergrinder.summary.data.database.InvestmentSummaryRoomEntity
import com.mctech.pokergrinder.summary.data.database.SessionSummaryRoomEntity
import com.mctech.pokergrinder.summary.data.database.TournamentSummaryRoomEntity
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary

/**
 * Converts a investment summary database entity onto a business one known by the modules.
 */
internal fun InvestmentSummaryRoomEntity.asBusinessInvestment() = InvestmentSummary(
  cash = cash,
  buyIn = buyIn,
  profit = profit,
)

/**
 * Converts a session summary database entity onto a business one known by the modules.
 */
internal fun SessionSummaryRoomEntity.asBusinessSession() = SessionSummary(
  total = total,
  upDays = upDays,
  downDays = downDays,
  tournaments = tournaments,
)

/**
 * Converts a tournament summary database entity onto a business one known by the modules.
 */
internal fun List<TournamentSummaryRoomEntity>.asBusinessTournament() = this.map {
  TournamentSummary(
    title = it.title,
    tournaments = it.tournaments,
    cash = it.cash,
    buyIn = it.buyIn,
    profit = it.profit,
    playedTimeInMs = it.playedTimeInMs,
  )
}