package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe grind flips.
 *
 * @property repository grind data repository.
 */
class ObserveGrindTournamentFlipsUseCase @Inject constructor(
  private val repository: GrindRepository,
) {

  operator fun invoke(sessionId: String): Flow<List<SessionTournamentFlip>> {
    return repository.observeGrindTournamentFlips(sessionId)
  }
}
