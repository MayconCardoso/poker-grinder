package com.mctech.pokergrinder.tournaments.domain.entities

import com.mctech.pokergrinder.tournaments.domain.newTournament
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class TournamentTest {
  @Test
  fun `should format tournament buy in`() {
    assertThat(newTournament(buyIn = 0F).formattedBuyIn()).isEqualTo("$0.00")
    assertThat(newTournament(buyIn = 1.65F).formattedBuyIn()).isEqualTo("$1.65")
    assertThat(newTournament(buyIn = 3.50F).formattedBuyIn()).isEqualTo("$3.50")
    assertThat(newTournament(buyIn = 10.0F).formattedBuyIn()).isEqualTo("$10.00")
    assertThat(newTournament(buyIn = 109.0F).formattedBuyIn()).isEqualTo("$109.00")
    assertThat(newTournament(buyIn = 215.0F).formattedBuyIn()).isEqualTo("$215.00")
    assertThat(newTournament(buyIn = 1050.0F).formattedBuyIn()).isEqualTo("$1050.00")
    assertThat(newTournament(buyIn = 10500.0F).formattedBuyIn()).isEqualTo("$10500.00")
  }
}