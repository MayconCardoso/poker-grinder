package com.mctech.pokergrinder.summary.data

import com.mctech.pokergrinder.summary.data.database.SummaryDao
import com.mctech.pokergrinder.summary.data.mapper.asBusinessInvestment
import com.mctech.pokergrinder.summary.data.mapper.asBusinessSession
import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class SummaryRepositoryImpl @Inject constructor(
  private val summaryDao: SummaryDao,
) : SummaryRepository {

  override fun observeSessionSummary(): Flow<SessionSummary> {
    return summaryDao.observeSessionSummary().map { it.asBusinessSession() }
  }

  override fun observeInvestmentSummary(): Flow<InvestmentSummary> {
    return summaryDao.observeInvestmentSummary().map { it.asBusinessInvestment() }
  }
}