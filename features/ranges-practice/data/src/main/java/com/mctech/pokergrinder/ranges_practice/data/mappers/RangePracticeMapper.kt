package com.mctech.pokergrinder.ranges_practice.data.mappers

import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeRoomEntity
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult

/**
 * Converts a list of answer database entity onto a business one known by the modules.
 */
internal fun List<RangePracticeRoomEntity>.asBusinessAnswers(): List<RangePracticeResult> {
  return this.map { dbEntity ->
    dbEntity.asBusiness()
  }
}

/**
 * Converts a answer database entity onto a business one known by the modules.
 */
internal fun RangePracticeRoomEntity.asBusiness() = RangePracticeResult(
  id = id,
  cards = cards,
  action = action,
  heroPosition = RangePlayerPosition.valueOf(heroPosition),
  villainPosition = villainPosition?.let { RangePlayerPosition.valueOf(it) },
  effectiveStack = effectiveStack,
  isAnswerCorrect = isAnswerCorrect,
)

/**
 * Converts a business answer onto a database one.
 */
internal fun RangePracticeResult.asDatabaseTransaction() = RangePracticeRoomEntity(
  id = id,
  cards = cards,
  action = action,
  heroPosition = heroPosition.name,
  villainPosition = villainPosition?.name,
  effectiveStack = effectiveStack,
  isAnswerCorrect = isAnswerCorrect,
  dateInMs = System.currentTimeMillis(),
)
