package com.mctech.pokergrinder.ranges.data

import com.mctech.pokergrinder.ranges.domain.RangesRepository
import com.mctech.pokergrinder.ranges.domain.entities.EmptyRangeHand
import com.mctech.pokergrinder.ranges.domain.entities.PlayerPosition
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeInput
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
    name = "14BB - UTG",
    position = PlayerPosition.UTG,
    effectiveStack = 14,
    hands = listOf(
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "A", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "K", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "Q", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "J", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "T", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "9", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "8", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "7", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "6", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "5", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "K", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "Q", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "J", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "T", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "K", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "Q", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "J", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "T", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "9", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "Q", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "K", secondCard = "J", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "Q", secondCard = "Q", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "Q", secondCard = "J", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "Q", secondCard = "T", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "Q", secondCard = "9", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "J", secondCard = "J", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "J", secondCard = "T", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "T", secondCard = "T", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "T", secondCard = "9", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "9", secondCard = "9", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "8", secondCard = "8", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "7", secondCard = "7", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "6", secondCard = "6", suited = false)),
    )
  )


  private fun createLj14Range() = Range(
    name = "14BB - LJ",
    position = PlayerPosition.UTG,
    effectiveStack = 14,
    hands = listOf(
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "A", suited = false)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "K", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "Q", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "J", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "T", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "9", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "8", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "7", suited = true)),
      RangeInput(hand = EmptyRangeHand(firstCard = "A", secondCard = "6", suited = true)),
    )
  )

}