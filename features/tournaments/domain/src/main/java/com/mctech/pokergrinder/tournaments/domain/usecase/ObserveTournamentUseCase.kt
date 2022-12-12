package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe tournaments.
 *
 * @property repository tournament data repository.
 */
class ObserveTournamentUseCase @Inject constructor(
  private val repository: TournamentRepository,
) {

  operator fun invoke(): Flow<List<Tournament>> {
    return repository.observe()
  }
}