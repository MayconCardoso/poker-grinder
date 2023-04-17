package com.mctech.pokergrinder.ranges_practice.domain

import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access range practice data.
 */
interface RangesPracticeRepository {

  /**
   * Used to observe all ranges practice.
   */
  fun observePracticeResult(): Flow<List<RangePracticeResult>>

  /**
   * Used to observe practice filter
   */
  fun observePracticeFilterResult(): Flow<RangePracticeFilter>

  /**
   * Used to save an answer.
   */
  suspend fun savePracticeAnswer(answer: RangePracticeResult)

  /**
   * Used to save an filter.
   */
  suspend fun savePracticeFilter(filter: RangePracticeFilter)
}