package com.mctech.pokergrinder.bankroll.domain.entities

import com.mctech.pokergrinder.bankroll.testing.newTransaction
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BankrollTransactionTest {

  @Test
  fun `assert formatted amount`() {
    assertThat(newTransaction(amount = 11.50).formattedAmount()).isEqualTo("$11.50")
    assertThat(newTransaction(amount = 1.10).formattedAmount()).isEqualTo("$1.10")
    assertThat(newTransaction(amount = 10000.0).formattedAmount()).isEqualTo("$10000.00")
  }
}