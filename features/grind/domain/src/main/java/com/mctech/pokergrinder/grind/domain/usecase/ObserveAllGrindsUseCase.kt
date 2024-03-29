package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe all grind sessions.
 *
 * @property repository grind data repository.
 */
class ObserveAllGrindsUseCase @Inject constructor(
  private val repository: GrindRepository,
) {

  operator fun invoke(): Flow<List<Session>> {
    return repository.observeAllGrinds()
  }

}