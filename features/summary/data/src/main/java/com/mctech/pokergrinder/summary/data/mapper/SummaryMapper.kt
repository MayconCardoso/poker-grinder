package com.mctech.pokergrinder.summary.data.mapper

import com.mctech.pokergrinder.summary.data.database.InvestmentSummaryRoomEntity
import com.mctech.pokergrinder.summary.data.database.SessionSummaryRoomEntity
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary

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