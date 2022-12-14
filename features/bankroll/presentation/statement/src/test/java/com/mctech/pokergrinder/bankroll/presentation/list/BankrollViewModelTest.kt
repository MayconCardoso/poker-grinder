package com.mctech.pokergrinder.bankroll.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveTransactionsUseCase
import com.mctech.pokergrinder.bankroll.presentation.newTransaction
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.junit.Test

internal class BankrollViewModelTest: BaseViewModelTest() {
  private val observeTransactionsUseCase = mockk<ObserveTransactionsUseCase>(relaxed = true)
  private val target = BankrollViewModel(
    observeTransactionsUseCase = observeTransactionsUseCase,
  )

  @Test
  fun `should start component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.transactionState) { stateStack ->
      stateStack.assertFlow(
        ComponentState.Loading.FromEmpty
      )
    }
  }

  @Test
  fun `should show statement`() = observerScenario {
    val transactions = listOf(
      newTransaction(id = "1"),
      newTransaction(id = "2")
    )

    val statementFlow = flow {
      emit(transactions)
    }

    givenScenario {
      every { observeTransactionsUseCase.invoke() } returns statementFlow
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.transactionState) { stateStack ->
      // Assert expected state
      stateStack.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(transactions)
      )

      // Assert expected calling functions.
      verifyOrder {
        observeTransactionsUseCase()
      }

      // Confirm no more calls for those instances.
      confirmVerified(observeTransactionsUseCase)
    }
  }

}