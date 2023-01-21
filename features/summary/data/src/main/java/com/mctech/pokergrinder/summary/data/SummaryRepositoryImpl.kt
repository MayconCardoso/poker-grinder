package com.mctech.pokergrinder.summary.data

import com.mctech.pokergrinder.summary.data.database.SummaryDao
import com.mctech.pokergrinder.summary.data.mapper.asBusinessInvestment
import com.mctech.pokergrinder.summary.data.mapper.asBusinessSession
import com.mctech.pokergrinder.summary.data.mapper.asBusinessTournament
import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SummaryRepositoryImpl @Inject constructor(
  private val summaryDao: SummaryDao,
) : SummaryRepository {

  override fun observeSessionSummary(): Flow<SessionSummary> {
    return summaryDao.observeSessionSummary().map { it.asBusinessSession() }
  }

  override fun observeInvestmentSummary(): Flow<InvestmentSummary> {
    return summaryDao.observeInvestmentSummary().map { it.asBusinessInvestment() }
  }

  override fun observeTournamentsSummary(): Flow<List<TournamentSummary>> {
    return summaryDao.observeTournamentSummary().map { it.asBusinessTournament() }
  }

  override fun observeTournamentDetails(tournamentSummary: TournamentSummary): Flow<List<TournamentSummary>> {
    return summaryDao.observeTournamentSummary(tournamentSummary.title)
      .map { it.asBusinessTournament() }
  }
}