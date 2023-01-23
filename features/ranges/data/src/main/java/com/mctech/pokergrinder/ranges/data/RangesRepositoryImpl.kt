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
    createUtg14Range(),
    createLj14Range(),
  )

  override fun observeAllRanges(): Flow<List<Range>> = flow {
    emit(mockedRanges)
  }

  override fun observeRangeByName(name: String): Flow<Range> = flow {
    emit(mockedRanges.first { it.name == name })
  }

  private fun createUtg14Range() = Range(
    name = "15BB - Open",
    action = RangeAction.OPEN,
    effectiveStack = 14,
    handPosition = listOf(
      RangePosition(
        position = RangePlayerPosition.UTG,
        hands = listOf(
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
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "K", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "6", suited = false)),
        )
      ),
      RangePosition(
        position = RangePlayerPosition.UTG,
        hands = listOf(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "A", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "6", suited = true)),
        ),
      )
    )
  )


  private fun createLj14Range() = Range(
    name = "30BB - Open",
    action = RangeAction.OPEN,
    effectiveStack = 14,
    handPosition = listOf(
      RangePosition(
        position = RangePlayerPosition.UTG,
        hands = listOf(
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
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "K", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "K", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "Q", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "Q", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "J", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "J", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "T", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "T", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "9", secondCard = "9", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "8", secondCard = "8", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "7", secondCard = "7", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "6", secondCard = "6", suited = false)),
        )
      ),
      RangePosition(
        position = RangePlayerPosition.UTG,
        hands = listOf(
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "A", suited = false)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "K", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "Q", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "J", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "T", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "9", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "8", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "7", suited = true)),
          RangeHandInput(hand = RangeHand(firstCard = "A", secondCard = "6", suited = true)),
        ),
      )
    )
  )

}