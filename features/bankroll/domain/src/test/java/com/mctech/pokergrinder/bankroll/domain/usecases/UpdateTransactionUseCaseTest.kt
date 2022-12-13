package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.newTransaction
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions
import org.junit.Test

class UpdateTransactionUseCaseTest {
  private val repository = mockk<BankrollRepository>(relaxed = true)
  private val target = UpdateTransactionUseCase(
    repository = repository,
  )

  @Test
  fun `should update transaction`() = simpleScenario {
    val saveTransaction = newTransaction(id = "10", amount = 50.0, )
    val expectedTransaction = saveTransaction.copy(amount = 150.0)

    givenScenario {
      coEvery { repository.load(any()) } returns saveTransaction
    }

    whenAction {
      target(id = "10", amount = 150.0)
    }

    thenAssert {
      // Creates spy
      val transactionSlotSpy = slot<BankrollTransaction>()

      // Checks calling pipeline
      coVerifyOrder {
        repository.load("10")
        repository.save(capture(transactionSlotSpy))
      }

      // Assert saved transaction
      Assertions.assertThat(transactionSlotSpy.captured).isEqualTo(expectedTransaction)
    }
  }
}