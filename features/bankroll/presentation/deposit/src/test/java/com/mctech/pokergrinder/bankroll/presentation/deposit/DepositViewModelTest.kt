package com.mctech.pokergrinder.bankroll.presentation.deposit

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollAnalytics
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.DepositUseCase
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.Test

internal class DepositViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<BankrollAnalytics>(relaxed = true)
  private val depositUseCase = mockk<DepositUseCase>(relaxed = true)
  private val target = DepositViewModel(
    analytics = analytics,
    depositUseCase = depositUseCase,
  )

  @Test
  fun `should deposit money`() = TestObserverScenario.observerScenario {
    whenAction {
      target.interact(
        DepositInteraction.SaveDeposit(amount = 100.0, title = "Tournament Profit")
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      Assertions.assertThat(commands).containsExactly(DepositCommand.CloseScreen)
    }

    thenAssert {
      coVerifyOrder {
        depositUseCase(
          amount = 100.0,
          description = "Tournament Profit",
          type = BankrollTransactionType.DEPOSIT
        )
        analytics.onDepositMade(amount = 100.0)
      }
      confirmVerified(depositUseCase, analytics)
    }
  }
}