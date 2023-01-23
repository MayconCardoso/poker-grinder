package com.mctech.pokergrinder.ranges.domain

import com.mctech.pokergrinder.ranges.domain.entities.Range
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access range data.
 */
interface RangesRepository {
  fun observeAllRanges(): Flow<List<Range>>
}