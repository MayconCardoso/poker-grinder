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
class ObserveRangeByNameUseCase @Inject constructor(
  private val repository: RangesRepository,
) {

  operator fun invoke(name: String): Flow<Range> {
    return repository.observeRangeByName(name)
  }

}