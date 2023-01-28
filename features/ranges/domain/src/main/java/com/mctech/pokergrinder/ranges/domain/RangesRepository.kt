package com.mctech.pokergrinder.ranges.domain

import com.mctech.pokergrinder.ranges.domain.entities.Range
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access range data.
 */
interface RangesRepository {

  /**
   * Used to observe all ranges.
   */
  fun observeAllRanges(): Flow<List<Range>>

  /**
   * Used to observe an specific [range].
   */
  fun observeRange(range: Range): Flow<Range>
}