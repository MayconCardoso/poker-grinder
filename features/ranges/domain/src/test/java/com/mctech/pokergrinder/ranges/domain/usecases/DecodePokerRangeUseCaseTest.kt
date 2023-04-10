package com.mctech.pokergrinder.ranges.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.ranges.domain.entities.RangeHand
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class DecodePokerRangeUseCaseTest {
  private val range = "66+,AJs+,KQs,AJo+,KQo,89s"
  private val decodePokerRangeUseCase = DecodePokerRangeUseCase()
  private val expectedHands = listOf(
    RangeHand("6", "6", false),
    RangeHand("7", "7", false),
    RangeHand("8", "8", false),
    RangeHand("9", "9", false),
    RangeHand("T", "T", false),
    RangeHand("J", "J", false),
    RangeHand("Q", "Q", false),
    RangeHand("K", "K", false),
    RangeHand("A", "A", false),
    RangeHand("A", "J", true),
    RangeHand("A", "Q", true),
    RangeHand("A", "K", true),
    RangeHand("K", "Q", true),
    RangeHand("A", "J", false),
    RangeHand("A", "Q", false),
    RangeHand("A", "K", false),
    RangeHand("K", "Q", false),
    RangeHand("8", "9", true),

  )

  @Test
  fun `should decode range of hands`() = responseScenario<List<RangeHand>> {

    whenAction {
      decodePokerRangeUseCase(range)
    }

    thenAssert { hands ->
      assertThat(hands).isEqualTo(expectedHands)
    }
  }
}