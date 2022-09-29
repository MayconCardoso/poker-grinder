package com.mctech.pokergrinder.summary.domain.usecases

import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveTournamentSummaryUseCase @Inject constructor(
  private val repository: SummaryRepository,
) {
  operator fun invoke(): Flow<List<TournamentSummary>> {
    return repository.observeTournamentsSummary()
      .map { it -> it.sortedByDescending { it.computeRoi() } }
  }
}