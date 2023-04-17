package com.mctech.pokergrinder.bankroll.presentation.withdraw

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollAnalytics
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import com.mctech.pokergrinder.bankroll.domain.usecases.WithdrawUseCase
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class WithdrawViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<BankrollAnalytics>(relaxed = true)
  private val withdrawUseCase = mockk<WithdrawUseCase>(relaxed = true)
  private val target = WithdrawViewModel(
    analytics = analytics,
    withdrawUseCase = withdrawUseCase,
  )

  @Test
  fun `should withdraw money`() = TestObserverScenario.observerScenario {
    whenAction {
      target.interact(
        WithdrawInteraction.SaveWithdraw(amount = 100.0, title = "Tournament Profit")
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).containsExactly(WithdrawCommand.CloseScreen)
    }

    thenAssert {
      coVerifyOrder {
        withdrawUseCase(
          amount = 100.0,
          description = "Tournament Profit",
          type = BankrollTransactionType.WITHDRAW
        )

        analytics.onWithdrawMade(amount = 100.0)
      }
      confirmVerified(withdrawUseCase, analytics)
    }
  }

  @Test
  fun `should not withdraw money when out of balance`() = TestObserverScenario.observerScenario {
    givenScenario {
      coEvery { withdrawUseCase(any(), any(), any()) } throws BankrollException.InsufficientBalance
    }

    whenAction {
      target.interact(
        WithdrawInteraction.SaveWithdraw(amount = 100.0, title = "Tournament Profit")
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).containsExactly(WithdrawCommand.InsufficientBalanceError)
    }

    thenAssert {
      coVerifyOrder {
        withdrawUseCase(
          amount = 100.0,
          description = "Tournament Profit",
          type = BankrollTransactionType.WITHDRAW
        )
      }
      confirmVerified(withdrawUseCase, analytics)
    }
  }

}