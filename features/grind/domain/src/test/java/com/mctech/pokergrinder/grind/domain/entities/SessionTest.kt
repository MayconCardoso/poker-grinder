package com.mctech.pokergrinder.grind.domain.entities

import com.mctech.pokergrinder.grind.testing.newSession
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SessionTest {

  private val sessions = listOf(
    newSession(cash = 0.0, buyIn = 0.0, avgBuyIn = 0.0),
    newSession(cash = 11.50, buyIn = 2.00, avgBuyIn = 1.00),
    newSession(cash = 345.0, buyIn = 111.00, avgBuyIn = 11.00),
    newSession(cash = 2750.0, buyIn = 995.0, avgBuyIn = 22.00),
    newSession(cash = 170.98, buyIn = 451.25, avgBuyIn = 5.25),
  )

  @Test
  fun `should check session is in profit`() {
    val data = sessions.map { it.isInProfit() }
    val expected = listOf(true, true, true, true, false)
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should compute balance`() {
    val data = sessions.map { it.computesBalance() }
    val expected = listOf(0.0, 9.50, 234.0, 1755.0, -280.27)
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should compute roi`() {
    val data = sessions.map { it.computesBalance() }
    val expected = listOf(0.0, 9.50, 234.0, 1755.0, -280.27)
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format balance`() {
    val data = sessions.map { it.formattedBalance() }
    val expected = listOf("$0.00", "$9.50", "$234.00", "$1755.00", "-$280.27")
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format cash`() {
    val data = sessions.map { it.formattedCash() }
    val expected = listOf("$0.00", "$11.50", "$345.00", "$2750.00", "$170.98")
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format buy in`() {
    val data = sessions.map { it.formattedBuyIn() }
    val expected = listOf("$0.00", "$2.00", "$111.00", "$995.00", "$451.25")
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format avg buy in`() {
    val data = sessions.map { it.formattedAvgBuyIn() }
    val expected = listOf("$0.00", "$1.00", "$11.00", "$22.00", "$5.25")
    assertThat(data).isEqualTo(expected)
  }

  @Test
  fun `should format roi`() {
    val data = sessions.map { it.formattedRoi() }
    val expected = listOf("0.00%", "475.00%", "210.81%", "176.38%", "-62.11%")
    assertThat(data).isEqualTo(expected)
  }
}