package com.mctech.pokergrinder.grind.presentation.flip_creation

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RegisterFlipFlowTest {

  @Test
  fun `should check one of`() {
    val target = RegisterFlipFlow.BOARD_PICKER
    assertThat(
      target.isOneOf(
        RegisterFlipFlow.WHO_WON,
        RegisterFlipFlow.TOURNAMENT_PICKER,
        RegisterFlipFlow.BOARD_PICKER,
      )
    )
  }

  @Test
  fun `should not check one of`() {
    val target = RegisterFlipFlow.BOARD_PICKER
    assertThat(
      target.isNotOneOf(
        RegisterFlipFlow.WHO_WON,
        RegisterFlipFlow.TOURNAMENT_PICKER,
        RegisterFlipFlow.HERO_CARD_PICKER,
      )
    )
  }
}