package com.mctech.pokergrinder.deck.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CardSuitTest {

  @Test
  fun `should assert suit`() {
    assertThat(CardSuit.CLUBS.suffix).isEqualTo("c")
    assertThat(CardSuit.DIAMONDS.suffix).isEqualTo("d")
    assertThat(CardSuit.HEARTS.suffix).isEqualTo("h")
    assertThat(CardSuit.SPADES.suffix).isEqualTo("s")
  }
}