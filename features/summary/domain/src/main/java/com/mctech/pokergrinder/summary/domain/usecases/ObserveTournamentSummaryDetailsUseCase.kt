package com.mctech.pokergrinder.summary.domain.usecases

import com.mctech.pokergrinder.summary.domain.SummaryRepository
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveTournamentSummaryDetailsUseCase @Inject constructor(
  private val repository: SummaryRepository,
) {
  operator fun invoke(tournamentSummary: TournamentSummary): Flow<List<TournamentSummary>> {
    return repository.observeTournamentDetails(tournamentSummary)
  }
}