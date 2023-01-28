package com.mctech.pokergrinder.ranges.domain.entities

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RangeHandTest {
  @Test
  fun `should not show off suit marker for pocket pairs`() {
    assertHand(firstCard = "A", secondCard = "A", expected = "AA")
    assertHand(firstCard = "K", secondCard = "K", expected = "KK")
    assertHand(firstCard = "Q", secondCard = "Q", expected = "QQ")
  }

  @Test
  fun `should show off suit marker`() {
    assertHand(firstCard = "A", secondCard = "K", expected = "AKo")
    assertHand(firstCard = "K", secondCard = "Q", expected = "KQo")
    assertHand(firstCard = "Q", secondCard = "J", expected = "QJo")
  }

  @Test
  fun `should show suit marker`() {
    assertHand(firstCard = "A", secondCard = "K", suited = true, expected = "AKs")
    assertHand(firstCard = "K", secondCard = "Q", suited = true, expected = "KQs")
    assertHand(firstCard = "Q", secondCard = "J", suited = true, expected = "QJs")
  }

  private fun assertHand(
    firstCard: String,
    secondCard: String,
    suited: Boolean = false,
    expected: String
  ) {
    assertThat(
      RangeHand(firstCard = firstCard, secondCard = secondCard, suited = suited).formattedName()
    ).isEqualTo(expected)
  }
}