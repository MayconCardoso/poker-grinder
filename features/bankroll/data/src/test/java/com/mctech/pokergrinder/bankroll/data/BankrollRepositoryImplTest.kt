package com.mctech.pokergrinder.bankroll.data

import com.mctech.architecture_testing.threading.TestCoroutineDispatcher
import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.bankroll.data.mapper.asBusinessTransaction
import com.mctech.pokergrinder.bankroll.data.mapper.asBusinessTransactions
import com.mctech.pokergrinder.bankroll.data.mapper.asDatabaseTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class BankrollRepositoryImplTest {

  private val dao = mockk<BankrollTransactionDao>(relaxed = true)
  private val target = BankrollRepositoryImpl(
    dispatchers = TestCoroutineDispatcher,
    bankrollDao = dao,
  )

  @Test
  fun `should delegate balance observe`() = responseScenario<Flow<Double>> {
    val flow = flow {
      emit(500.0)
    }

    givenScenario {
      every { dao.observeBalance() } returns flow
    }

    whenAction {
      target.observeBalance()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(500.0)
      coVerifyOrder { dao.observeBalance() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate transaction flow`() = responseScenario<Flow<List<BankrollTransaction>>> {
    val items = listOf(
      newDatabaseTransaction(id = "1"),
      newDatabaseTransaction(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { dao.observe() } returns flow
    }

    whenAction {
      target.observeTransactions()
    }

    thenAssert { result ->
      assertThat(result.last()).isEqualTo(items.asBusinessTransactions())
      coVerifyOrder { dao.observe() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate save flow`() = simpleScenario {
    val transaction = newTransaction(id = "1")

    whenAction {
      target.save(transaction)
    }

    thenAssert {
      val transactionSlot = slot<BankrollTransactionRoomEntity>()
      coVerify { dao.save(capture(transactionSlot)) }
      assertThat(transactionSlot.captured).isEqualTo(transaction.asDatabaseTransaction())

      coVerifyOrder { dao.save(any()) }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate load balance`() = responseScenario<Double> {
    givenScenario {
      coEvery { dao.loadBalance() } returns 1000.0
    }

    whenAction {
      target.loadBalance()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(1000.0)
      coVerifyOrder { dao.loadBalance() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate load`() = responseScenario<BankrollTransaction> {
    val databaseEntity = newDatabaseTransaction(id = "10")

    givenScenario {
      coEvery { dao.load(any()) } returns databaseEntity
    }

    whenAction {
      target.load("10")
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(databaseEntity.asBusinessTransaction())
      coVerifyOrder { dao.load(any()) }
      confirmVerified(dao)
    }
  }
}