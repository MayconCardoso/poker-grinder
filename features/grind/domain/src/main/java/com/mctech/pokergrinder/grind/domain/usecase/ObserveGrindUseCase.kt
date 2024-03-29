package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe an specific grind.
 *
 * @property repository grind data repository.
 */
class ObserveGrindUseCase @Inject constructor(
  private val repository: GrindRepository,
) {

  operator fun invoke(sessionId: String): Flow<Session> {
    return repository.observeGrind(sessionId)
  }
}
