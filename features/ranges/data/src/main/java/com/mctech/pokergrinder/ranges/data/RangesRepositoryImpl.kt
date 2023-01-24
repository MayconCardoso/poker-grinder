package com.mctech.pokergrinder.ranges.data

import com.mctech.pokergrinder.ranges.domain.RangesRepository
import com.mctech.pokergrinder.ranges.domain.entities.RangeHand
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RangesRepositoryImpl @Inject constructor() : RangesRepository {

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
      RangePosition(
        position = RangePlayerPosition.UTG,
        hands = baseRangeUtg50BigBlinds()
      ),
      RangePosition(
        position = RangePlayerPosition.UTG1,
        hands = baseRangeUtg50BigBlinds(
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "3", secondCard = "3", suited = false)),
        ),
      ),
      RangePosition(
        position = RangePlayerPosition.LJ,
        hands = baseRangeUtg50BigBlinds(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "3", secondCard = "3", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "2", secondCard = "2", suited = false)),
        ),
      ),
      RangePosition(
        position = RangePlayerPosition.HJ,
        hands = baseRangeUtg50BigBlinds(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "3", secondCard = "3", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "2", secondCard = "2", suited = false)),
        ),
      ),
      RangePosition(
        position = RangePlayerPosition.CO,
        hands = baseRangeUtg50BigBlinds(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "5", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "5", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "3", secondCard = "3", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "2", secondCard = "2", suited = false)),
        ),
      ),
      RangePosition(
        position = RangePlayerPosition.BTN,
        hands =  baseRangeUtg50BigBlinds(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "6", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "5", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "4", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "3", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "2", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "6", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "2", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "6", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "5", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "5", secondCard = "4", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "5", secondCard = "3", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "3", secondCard = "3", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "2", secondCard = "2", suited = false)),
        ),
      ),
    )
  )

  private fun baseRangeUtg50BigBlinds(vararg rangeHandInput: RangeHandInput) = buildList {
    addAll(
      listOf(
        // A
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "A", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "7", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "6", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "5", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "4", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "3", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = false)),

        // K
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "K", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "9", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "8", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = false)),

        // Q
        RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "Q", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "9", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = false)),

        // J
        RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "J", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "9", suited = true)),

        // T
        RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "T", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "9", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "8", suited = true)),

        // 9
        RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "9", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "8", suited = true)),
        RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "8", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "7", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "6", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "5", secondCard = "5", suited = false)),
        RangeHandInput(hand = RangeHand(firstCard = "4", secondCard = "4", suited = false)),
      )
    )
    addAll(rangeHandInput)
  }
}