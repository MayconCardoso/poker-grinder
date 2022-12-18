package com.mctech.pokergrinder.bankroll.presentation.balance_component

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveFormattedBalanceUseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.junit.Test

internal class BankrollBalanceViewModelTest : BaseViewModelTest() {
  private val observeBalanceUseCase = mockk<ObserveFormattedBalanceUseCase>(relaxed = true)
  private val target = BankrollBalanceViewModel(
    observeBalanceUseCase = observeBalanceUseCase,
  )

  @Test
  fun `should start component`() = TestObserverScenario.observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.balanceState) { stateStack ->
      stateStack.assertFlow(
        ComponentState.Loading.FromEmpty
      )
    }
  }

  @Test
  fun `should show balance`() = TestObserverScenario.observerScenario {
    val statementFlow = flow {
      emit("$1000.00")
    }

    givenScenario {
      every { observeBalanceUseCase.invoke() } returns statementFlow
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.balanceState) { stateStack ->
      // Assert expected state
      stateStack.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success("$1000.00"),
      )
    }

    thenAssert {
      // Assert expected calling functions.
      verifyOrder {
        observeBalanceUseCase()
      }

      // Confirm no more calls for those instances.
      confirmVerified(observeBalanceUseCase)
    }
  }

}