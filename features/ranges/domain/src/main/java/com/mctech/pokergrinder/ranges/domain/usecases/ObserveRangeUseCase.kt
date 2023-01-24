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
class ObserveRangeUseCase @Inject constructor(
  private val repository: RangesRepository,
) {

  operator fun invoke(range: Range): Flow<Range> {
    return repository.observeRange(range)
  }

}