package com.mctech.pokergrinder.ranges_practice.domain.usecases

import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import javax.inject.Inject

/**
 * Used to save a training answer.
 *
 * @property repository grind data repository.
 */
class SaveRangePracticeAnswerUseCase @Inject constructor(
  private val repository: RangesPracticeRepository,
) {

  operator fun invoke(answer: RangePracticeResult) {
    repository.savePracticeAnswer(answer)
  }

}