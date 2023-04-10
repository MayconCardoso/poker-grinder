package com.mctech.pokergrinder.ranges.data

import com.mctech.pokergrinder.ranges.domain.RangesRepository
import com.mctech.pokergrinder.ranges.domain.entities.*
import com.mctech.pokergrinder.ranges.domain.usecases.DecodePokerRangeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RangesRepositoryImpl @Inject constructor(
  private val decodePokerRangeUseCase: DecodePokerRangeUseCase,
) : RangesRepository {

  private val mockedRanges = listOf(
    createOpenRange50BigBlinds(),
  )

  override fun observeAllRanges(): Flow<List<Range>> = flow {
    emit(mockedRanges)
  }

  override fun observeRange(range: Range): Flow<Range> = flow {
    emit(mockedRanges.first { it.id == range.id })
  }

  private fun createOpenRange50BigBlinds() = Range(
    id = "1",
    name = "50BB - Open",
    action = RangeAction.OPEN,
    effectiveStack = 50,
    handPosition = listOf(
      createRangeForPosition(RangePlayerPosition.UTG, "44+,A3s+,K8s+,Q9s+,J9s+,T8s+,98s,ATo+,KJo+,QJo+"),
      createRangeForPosition(RangePlayerPosition.UTG1, "33+,A3s+,K6s+,Q9s+,J9s+,T8s+,98s+,ATo+,KJo+,QJo+"),
      createRangeForPosition(RangePlayerPosition.LJ, "22+,A2s+,K6s+,Q8s+,J8s+,T8s+,97s+,87s,76s,A9o+,KTo+,QTo+,JTo"),
      createRangeForPosition(RangePlayerPosition.HJ, "22+,A2s+,K4s+,Q6s+,J7s+,T7s+,97s+,86s+,76s,A8o+,KTo+,QTo+,JTo"),
      createRangeForPosition(RangePlayerPosition.CO, "22+,A2s+,K2s+,Q4s+,J6s+,T6s+,96s+,86s+,75s+,65s,54s,A7o+,A5o,K9o+,Q9o+,J9o+,T9o"),
      createRangeForPosition(RangePlayerPosition.BTN, "22+,A2s+,K2s+,Q2s+,J2s+,T3s+,95s+,86s+,74s+,64s+,53s+,A2o+,K6o+,Q7o+,J8o+,T8o+,98o+87o"),
    )
  )

  private fun createRangeForPosition(position: RangePlayerPosition, rangeNotation: String) = RangePosition(
    hands = decodePokerRangeUseCase(rangeNotation).map { RangeHandInput(hand = it) },
    position = position
  )
}