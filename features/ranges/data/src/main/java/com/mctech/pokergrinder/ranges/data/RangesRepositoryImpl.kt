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
    createOpenRange30BigBlinds(),
    createOpenRange50BigBlinds(),
  )

  override fun observeAllRanges(): Flow<List<Range>> = flow {
    emit(mockedRanges)
  }

  override fun observeRange(range: Range): Flow<Range> = flow {
    emit(mockedRanges.first { it.id == range.id })
  }

  private fun createOpenRange30BigBlinds() = Range(
    id = "1",
    name = "30BB - Open",
    action = RangeAction.OPEN,
    effectiveStack = 30,
    handPosition = listOf(
      createRangeForPosition(RangePlayerPosition.UTG, "55+,A3s+,K8s+,Q9s+,J9s+,T8s+,98s,ATo+,KTo+"),
      createRangeForPosition(RangePlayerPosition.UTG1, "55+,A3s+,K7s+,Q8s+,J9s+,T8s+,98s+,A9o+,KTo+"),
      createRangeForPosition(RangePlayerPosition.LJ, "44+,A2s+,K6s+,Q8s+,J8s+,T8s+,97s+,87s,A8o+,KTo+,QTo+,JTo+"),
      createRangeForPosition(RangePlayerPosition.HJ, "44+,A2s+,K5s+,Q6s+,J7s+,T7s+,97s+,87s+,76s,A7o+,K9o+,QTo+,JTo"),
      createRangeForPosition(RangePlayerPosition.CO, "44+,A2s+,K3s+,Q5s+,J7s+,T7s+,97s+,86s+,76s+,65s,A5o+,K9o+,Q9o+,J9o+,T9o"),
      createRangeForPosition(RangePlayerPosition.BTN, "44+,A2s+,K2s+,Q3s+,J4s+,T5s+,96s+,86s+,75s+,65s+,54s+,A2o+,K7o+,Q8o+,J8o+,T8o+,98o+"),
    )
  )

  private fun createOpenRange50BigBlinds() = Range(
    id = "2",
    name = "50BB - Open",
    action = RangeAction.OPEN,
    effectiveStack = 50,
    handPosition = listOf(
      createRangeForPosition(RangePlayerPosition.UTG, "55+,A3s+,K8s+,Q9s+,J9s+,T8s+,ATo+,KJo+"),
      createRangeForPosition(RangePlayerPosition.UTG1, "55+,A2s+,K7s+,Q9s+,J9s+,T8s+,98s+,ATo+,KTo+"),
      createRangeForPosition(RangePlayerPosition.LJ, "44+,A2s+,K5s+,Q9s+,J8s+,T8s+,98s+,87s,A9o+,KTo+,JTo"),
      createRangeForPosition(RangePlayerPosition.HJ, "33+,A2s+,K4s+,Q8s+,J7s+,T7s+,97s+,86s+,76s,65s,54s,A8o+,KTo+,QTo+,JTo"),
      createRangeForPosition(RangePlayerPosition.CO, "22+,A2s+,K2s+,Q4s+,J6s+,T6s+,96s+,85s+,75s+,65s,54s,A5o+,K9o+,Q9o+,J9o+,T9o"),
      createRangeForPosition(RangePlayerPosition.BTN, "22+,A2s+,K2s+,Q2s+,J3s+,T4s+,95s+,85s+,74s+,64s+,53s+,A2o+,K6o+,Q8o+,J8o+,T8o+,97o+"),
    )
  )

  private fun createRangeForPosition(position: RangePlayerPosition, rangeNotation: String) = RangePosition(
    hands = decodePokerRangeUseCase(rangeNotation).map { RangeHandInput(hand = it) },
    position = position
  )
}