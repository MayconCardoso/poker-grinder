package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.common_test.CalendarTestRule
import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.testing.newTransaction
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class WithdrawUseCaseTest {

  @get:Rule
  val calendarRule = CalendarTestRule()

  private val repository = mockk<BankrollRepository>(relaxed = true)
  private val generateUniqueIdUseCase = mockk<GenerateUniqueIdUseCase>(relaxed = true)

  private val target = WithdrawUseCase(
    repository = repository,
    generateUniqueIdUseCase = generateUniqueIdUseCase,
  )

  @Test
  fun `should create a withdraw`() = internalTestExecutor(
    id = "10",
    amount = 1000.0,
    balance = 5000.0,
    description = "This is a deposit",
    type = BankrollTransactionType.WITHDRAW,
  )

  @Test
  fun `should create a buy in transaction`() = internalTestExecutor(
    id = "25",
    amount = 64.0,
    balance = 100.0,
    description = "This is a profit",
    type = BankrollTransactionType.BUY_IN,
  )

  private fun internalTestExecutor(
    id: String,
    amount: Double,
    balance: Double,
    description: String,
    type: BankrollTransactionType,
  ) = responseScenario<String> {

    val expectedTransaction = newTransaction(
      id = id,
      type = type,
      amount = -amount,
      description = description,
    )

    givenScenario {
      coEvery { calendarRule.calendar.timeInMillis } returns 0
      coEvery { generateUniqueIdUseCase() } returns id
    }

    whenAction {
      target(amount = amount, description = description, type = type)
    }

    thenAssert { result ->
      // Creates spy
      val transactionSlotSpy = slot<BankrollTransaction>()

      // Checks generated ID.
      assertThat(result).isEqualTo(id)

      // Checks calling pipeline
      coVerifyOrder {
        generateUniqueIdUseCase()
        repository.save(capture(transactionSlotSpy))
      }

      // Assert saved transaction
      assertThat(transactionSlotSpy.captured).isEqualTo(expectedTransaction)
    }
  }
}