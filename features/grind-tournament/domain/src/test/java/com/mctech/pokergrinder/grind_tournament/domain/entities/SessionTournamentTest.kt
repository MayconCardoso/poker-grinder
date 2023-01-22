package com.mctech.pokergrinder.grind_tournament.domain.entities

import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import org.assertj.core.api.Assertions
import org.junit.Test

class SessionTournamentTest {

  private val sessionTournaments = listOf(
    newTournament(profit = 11.50, buyIn = 2.00),
    newTournament(profit = 345.0, buyIn = 111.00),
    newTournament(profit = 2750.0, buyIn = 995.0),
    newTournament(profit = 170.98, buyIn = 451.25),
  )


  @Test
  fun `should compute balance`() {
    val data = sessionTournaments.map { it.computesBalance() }
    val expected = listOf(9.50, 234.0, 1755.0, -280.27)
    Assertions.assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format balance`() {
    val data = sessionTournaments.map { it.formattedBalance() }
    val expected = listOf("$9.50", "$234.00", "$1755.00", "-$280.27")
    Assertions.assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format cash`() {
    val data = sessionTournaments.map { it.formattedProfit() }
    val expected = listOf("$11.50", "$345.00", "$2750.00", "$170.98")
    Assertions.assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format buy in`() {
    val data = sessionTournaments.map { it.formattedBuyIn() }
    val expected = listOf("$2.00", "$111.00", "$995.00", "$451.25")
    Assertions.assertThat(data).isEqualTo(expected)
  }
}