package com.mctech.pokergrinder.summary.domain.usecases

import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSessionSummaryUseCase @Inject constructor(
  private val repository: SummaryRepository,
) {
  operator fun invoke(): Flow<SessionSummary> {
    return repository.observeSessionSummary()
  }
}