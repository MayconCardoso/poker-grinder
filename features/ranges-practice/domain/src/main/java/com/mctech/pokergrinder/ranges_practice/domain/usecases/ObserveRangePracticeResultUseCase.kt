package com.mctech.pokergrinder.ranges_practice.domain.usecases

import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe all ranges.
 *
 * @property repository grind data repository.
 */
class ObserveRangePracticeResultUseCase @Inject constructor(
  private val repository: RangesPracticeRepository,
) {

  operator fun invoke(): Flow<List<RangePracticeResult>> {
    return repository.observePracticeResult()
  }

}