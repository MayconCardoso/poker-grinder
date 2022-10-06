package com.mctech.pokergrinder.summary.domain

import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import kotlinx.coroutines.flow.Flow

interface SummaryRepository {
  fun observeSessionSummary(): Flow<SessionSummary>
  fun observeInvestmentSummary(): Flow<InvestmentSummary>
  fun observeTournamentsSummary(): Flow<List<TournamentSummary>>
}