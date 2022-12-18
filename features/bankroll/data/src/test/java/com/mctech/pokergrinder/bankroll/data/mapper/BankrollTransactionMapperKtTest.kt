package com.mctech.pokergrinder.bankroll.data.mapper

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.bankroll.data.newDatabaseTransaction
import com.mctech.pokergrinder.bankroll.testing.newTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class BankrollTransactionMapperKtTest {

  private val database = newDatabaseTransaction(
    id = "10",
    type = BankrollTransactionType.BUY_IN,
    amount = 1999.0,
    description = "Hey",
    dateInMs = 1292,
  )

  private val business = newTransaction(
    id = "10",
    type = BankrollTransactionType.BUY_IN,
    amount = 1999.0,
    description = "Hey",
    dateInMs = 1292,
  )

  @Test
  fun `should convert database to business`() = responseScenario<BankrollTransaction>{
    whenAction {
      database.asBusinessTransaction()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(business)
    }
  }

  @Test
  fun `should convert business to database`() = responseScenario<BankrollTransactionRoomEntity>{
    whenAction {
      business.asDatabaseTransaction()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(database)
    }
  }

  @Test
  fun `should convert list of database to business`() = responseScenario<List<BankrollTransaction>>{
    whenAction {
      listOf(database).asBusinessTransactions()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(listOf(business))
    }
  }
}