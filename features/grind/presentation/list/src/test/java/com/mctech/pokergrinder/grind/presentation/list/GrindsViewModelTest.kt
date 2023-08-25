package com.mctech.pokergrinder.grind.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import io.mockk.*
import kotlinx.coroutines.flow.flow
import org.junit.Test

internal class GrindsViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<GrindAnalytics>(relaxed = true)
  private val observeAllGrindsUseCase = mockk<ObserveAllGrindsUseCase>(relaxed = true)
  private val observeSettingsUseCase = mockk<ObserveSettingsUseCase>(relaxed = true)
  private val target = GrindsViewModel(
    analytics = analytics,
    observeSettingsUseCase = observeSettingsUseCase,
    observeAllGrindsUseCase = observeAllGrindsUseCase,
  )

  @Test
  fun `should initialize component with visible data`() = showDataTest(
    shouldHideData = false,
    hideBalanceSettingsEnabled = false,
  )

  @Test
  fun `should initialize component with hidden data`() = showDataTest(
    shouldHideData = true,
    hideBalanceSettingsEnabled = true,
  )

  @Test
  fun `should navigate to session when it is clicked`() = observerScenario {
    val clickedSession = newSession(id = "1")
    val clickedState = newState(clickedSession, false)

    whenAction {
      target.interact(
        GrindsInteraction.OnGrindEvent(GrindAdapterConsumerEvent.SessionClicked(clickedState))
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindsCommand.NavigateToSessionDetails(clickedSession),
    )

    thenAssert {
      coVerifyOrder {
        analytics.onSessionViewed(clickedSession.title)
      }
      confirmVerified(observeAllGrindsUseCase, analytics)
    }
  }

  @Test
  fun `should navigate to new session when new session button is clicked`() = observerScenario {
    whenAction {
      target.interact(GrindsInteraction.NewSessionClicked)
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindsCommand.NavigateToNewSession,
    )

    thenAssert {
      coVerifyOrder {
        analytics.onCreateSessionClicked()
      }
      confirmVerified(observeAllGrindsUseCase, analytics)
    }
  }

  private fun mockSettings(hideBalance: Boolean) {
    every { observeSettingsUseCase(SettingsAvailable.HIDE_BANKROLL_BALANCE) } returns flow {
      emit(
        Settings(
          key = SettingsAvailable.HIDE_BANKROLL_BALANCE.key,
          value = hideBalance.toString(),
          title = ""
        )
      )
    }
  }

  private fun newState(
    session: Session,
    shouldHideBalance: Boolean,
  ) = GrindState(
    session = session,
    title = session.title,
    tournaments = session.tournamentsPlayed,
    cash = if (shouldHideBalance) "*****" else session.formattedCash(),
    buyIn = if (shouldHideBalance) "*****" else session.formattedBuyIn(),
    avgBuyIn = if (shouldHideBalance) "*****" else session.formattedAvgBuyIn(),
    profit = if (shouldHideBalance) "*****" else session.formattedBalance(),
    roi = session.formattedRoi(),
  )

  private fun showDataTest(hideBalanceSettingsEnabled: Boolean, shouldHideData: Boolean) = observerScenario {
    val sessions = listOf(newSession(id = "1"), newSession(id = "2"))
    val states = sessions.map { newState(it, shouldHideData) }

    givenScenario {
      mockSettings(hideBalance = hideBalanceSettingsEnabled)
      every { observeAllGrindsUseCase() } returns flow { emit(sessions) }
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.componentState) { stateList ->
      stateList.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(states),
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      verifyOrder { observeAllGrindsUseCase() }
      confirmVerified(observeAllGrindsUseCase, analytics)
    }
  }
}