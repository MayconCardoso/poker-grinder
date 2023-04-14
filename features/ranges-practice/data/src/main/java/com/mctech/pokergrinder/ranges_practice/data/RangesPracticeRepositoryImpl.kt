package com.mctech.pokergrinder.ranges_practice.data

import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeDao
import com.mctech.pokergrinder.ranges_practice.data.mappers.asBusinessAnswers
import com.mctech.pokergrinder.ranges_practice.data.mappers.asDatabaseTransaction
import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RangesPracticeRepositoryImpl @Inject constructor(
  private val rangePracticeDao: RangePracticeDao,
) : RangesPracticeRepository {

  override fun observePracticeResult(): Flow<List<RangePracticeResult>> {
    return rangePracticeDao.observe().map { it.asBusinessAnswers() }
  }

  override fun observePracticeFilterResult(): Flow<RangePracticeFilter> {
    return flow {
      emit(
        RangePracticeFilter()
      )
    }
  }

  override suspend fun savePracticeAnswer(answer: RangePracticeResult) {
    rangePracticeDao.save(answer.asDatabaseTransaction())
  }
}