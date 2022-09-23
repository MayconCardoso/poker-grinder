package com.mctech.pokergrinder.summary.domain.usecases

import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveInvestmentSummaryUseCase @Inject constructor(
  private val repository: SummaryRepository,
) {
  operator fun invoke(): Flow<InvestmentSummary> {
    return repository.observeInvestmentSummary()
  }
}