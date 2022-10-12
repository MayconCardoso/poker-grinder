package com.mctech.pokergrinder.bankroll.domain.entities

import com.mctech.pokergrinder.bankroll.domain.BankrollTransactionData
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BankrollTransactionTest {

  @Test
  fun `assert formatted amount`() {
    assertThat(BankrollTransactionData.single(amount = 11.50).formattedAmount()).isEqualTo("$11.50")
    assertThat(BankrollTransactionData.single(amount = 1.10).formattedAmount()).isEqualTo("$1.10")
    assertThat(BankrollTransactionData.single(amount = 10000.0).formattedAmount()).isEqualTo("$10000.00")
  }


  @Test
  fun `assert formatted date`() {
    assertThat(BankrollTransactionData.single(dateInMs = 1665587107521).formattedDate()).isEqualTo("12 Oct 2022")
  }

}