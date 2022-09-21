package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTournamentUseCase @Inject constructor(
  private val repository: TournamentRepository,
) {
  operator fun invoke(tournament: Tournament): Flow<List<Tournament>> {
    return repository.observe()
  }
}