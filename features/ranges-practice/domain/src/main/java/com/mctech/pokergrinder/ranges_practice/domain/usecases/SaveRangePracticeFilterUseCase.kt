package com.mctech.pokergrinder.ranges_practice.domain.usecases

import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import javax.inject.Inject

/**
 * Used to save a training filter.
 *
 * @property repository grind data repository.
 */
class SaveRangePracticeFilterUseCase @Inject constructor(
  private val repository: RangesPracticeRepository,
) {

  suspend operator fun invoke(filter: RangePracticeFilter) {
    // Saves filter
    repository.savePracticeFilter(filter)
  }

}