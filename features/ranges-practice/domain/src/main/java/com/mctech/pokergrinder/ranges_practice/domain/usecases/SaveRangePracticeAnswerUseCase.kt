package com.mctech.pokergrinder.ranges_practice.domain.usecases

import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeQuestion
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import java.util.*
import javax.inject.Inject

/**
 * Used to save a training answer.
 *
 * @property repository grind data repository.
 */
class SaveRangePracticeAnswerUseCase @Inject constructor(
  private val repository: RangesPracticeRepository,
) {

  suspend operator fun invoke(
    question: RangePracticeQuestion,
    takenAction: RangeAction,
    isAnswerCorrect: Boolean,
  ) {
    // Creates answer
    val result = RangePracticeResult(
      id = UUID.randomUUID().toString(),
      cards = question.cards,
      action = takenAction.name,
      heroPosition = question.heroPosition,
      villainPosition = question.villainPosition,
      effectiveStack = question.stack,
      isAnswerCorrect = isAnswerCorrect,
    )

    // Saves answer
    repository.savePracticeAnswer(result)
  }

}