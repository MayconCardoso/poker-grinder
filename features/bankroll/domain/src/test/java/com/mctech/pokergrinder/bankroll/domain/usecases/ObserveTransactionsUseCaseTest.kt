package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.newTransaction
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions
import org.junit.Test

class ObserveTransactionsUseCaseTest {

  private val repository = mockk<BankrollRepository>(relaxed = true)
  private val target = ObserveTransactionsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<BankrollTransaction>>> {
    val items = listOf(
      newTransaction(id = "1"),
      newTransaction(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observeTransactions() } returns flow
    }

    whenAction {
      target.invoke()
    }

    thenAssert { result ->
      Assertions.assertThat(result).isEqualTo(flow)
      Assertions.assertThat(result.last()).isEqualTo(items)
    }
  }
}