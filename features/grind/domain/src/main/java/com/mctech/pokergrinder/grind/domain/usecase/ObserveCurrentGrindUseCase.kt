package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe current in progress grind session.
 *
 * @property repository grind data repository.
 */
class ObserveCurrentGrindUseCase @Inject constructor(
  private val repository: GrindRepository,
) {

  operator fun invoke(): Flow<Session?> {
    return repository.observeCurrentGrind()
  }

}