package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe grind tournaments.
 *
 * @property repository grind data repository.
 */
class ObserveGrindTournamentUseCase @Inject constructor(
  private val repository: GrindRepository,
) {

  operator fun invoke(sessionId: String): Flow<List<SessionTournament>> {
    return repository.observeGrindTournament(sessionId)
  }
}
