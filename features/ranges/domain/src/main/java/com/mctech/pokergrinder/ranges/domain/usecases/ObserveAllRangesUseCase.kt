package com.mctech.pokergrinder.ranges.domain.usecases

import com.mctech.pokergrinder.ranges.domain.RangesRepository
import com.mctech.pokergrinder.ranges.domain.entities.Range
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe all ranges.
 *
 * @property repository grind data repository.
 */
class ObserveAllRangesUseCase @Inject constructor(
  private val repository: RangesRepository,
) {

  operator fun invoke(): Flow<List<Range>> {
    return repository.observeAllRanges()
  }

}