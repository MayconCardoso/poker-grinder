package com.mctech.pokergrinder.ranges_practice.data

import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RangesPracticeRepositoryImpl @Inject constructor() : RangesPracticeRepository {
  override fun observePracticeResult(): Flow<List<RangePracticeResult>> {
    return flow {
      emit(
        listOf(

        )
      )
    }
  }

  override fun savePracticeAnswer(answer: RangePracticeResult) {

  }
}