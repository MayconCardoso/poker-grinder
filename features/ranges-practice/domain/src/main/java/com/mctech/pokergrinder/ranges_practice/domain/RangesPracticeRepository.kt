package com.mctech.pokergrinder.ranges_practice.domain

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
   * Used to observe all ranges practice.
   */
  fun savePracticeAnswer(answer: RangePracticeResult)
}