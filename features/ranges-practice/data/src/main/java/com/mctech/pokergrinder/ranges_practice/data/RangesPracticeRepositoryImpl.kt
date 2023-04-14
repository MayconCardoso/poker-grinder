package com.mctech.pokergrinder.ranges_practice.data

import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RangesPracticeRepositoryImpl @Inject constructor() : RangesPracticeRepository {

  private val items = mutableListOf<RangePracticeResult>()
  private val flow = MutableStateFlow(items)

  override fun observePracticeResult(): Flow<List<RangePracticeResult>> {
    return flow
  }

  override fun observePracticeFilterResult(): Flow<RangePracticeFilter> {
    return flow {
      emit(
        RangePracticeFilter()
      )
    }
  }

  override fun savePracticeAnswer(answer: RangePracticeResult) {
    items.add(answer)
    flow.value = items
  }
}