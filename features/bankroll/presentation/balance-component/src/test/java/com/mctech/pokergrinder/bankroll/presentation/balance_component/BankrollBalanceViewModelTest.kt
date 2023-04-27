package com.mctech.pokergrinder.bankroll.presentation.balance_component

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveFormattedBalanceUseCase
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import com.mctech.pokergrinder.settings.domain.usecase.SaveSettingsUseCase
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.junit.Test

internal class BankrollBalanceViewModelTest : BaseViewModelTest() {
  private val observeBalanceUseCase = mockk<ObserveFormattedBalanceUseCase>(relaxed = true)
  private val saveSettingsUseCase = mockk<SaveSettingsUseCase>(relaxed = true)
  private val observeSettingsUseCase = mockk<ObserveSettingsUseCase>(relaxed = true)
  private val target = BankrollBalanceViewModel(
    observeBalanceUseCase = observeBalanceUseCase,
    saveSettingsUseCase = saveSettingsUseCase,
    observeSettingsUseCase = observeSettingsUseCase,
  )

  @Test
  fun `should start component`() = observerScenario {
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
  fun `should show balance`() = internalBalanceTest(
    settingsValue = "false",
    expectedBalance = "$1000.00"
  )

  @Test
  fun `should hide balance`() = internalBalanceTest(
    settingsValue = "true",
    expectedBalance = "******"
  )

  private fun internalBalanceTest(
    settingsValue: String,
    expectedBalance: String,
  ) = observerScenario {
    val statementFlow = flow {
      emit("$1000.00")
    }
    val settingsFlow = flow {
      emit(
        Settings(
          key = SettingsAvailable.HIDE_BANKROLL_BALANCE.key,
          value = settingsValue,
          title = ""
        )
      )
    }

    givenScenario {
      every { observeBalanceUseCase.invoke() } returns statementFlow
      every { observeSettingsUseCase.invoke(any()) } returns settingsFlow
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.balanceState) { stateStack ->
      // Assert expected state
      stateStack.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedBalance),
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