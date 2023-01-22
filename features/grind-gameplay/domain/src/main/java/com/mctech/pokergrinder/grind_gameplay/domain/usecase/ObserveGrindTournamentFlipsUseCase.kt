package com.mctech.pokergrinder.grind_gameplay.domain.usecase

import com.mctech.pokergrinder.grind_gameplay.domain.GrindGameplayRepository
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe grind flips.
 *
 * @property repository grind data repository.
 */
class ObserveGrindTournamentFlipsUseCase @Inject constructor(
  private val repository: GrindGameplayRepository,
) {

  operator fun invoke(sessionId: String): Flow<List<SessionTournamentFlip>> {
    return repository.observeGrindTournamentFlips(sessionId)
  }
}
