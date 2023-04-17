package com.mctech.pokergrinder.grind.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent
import com.mctech.pokergrinder.grind.testing.newSession
import io.mockk.*
import kotlinx.coroutines.flow.flow
import org.junit.Test

internal class GrindsViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<GrindAnalytics>(relaxed = true)
  private val observeAllGrindsUseCase = mockk<ObserveAllGrindsUseCase>(relaxed = true)
  private val target = GrindsViewModel(
    analytics = analytics,
    observeAllGrindsUseCase = observeAllGrindsUseCase,
  )

  @Test
  fun `should initialize component`() = observerScenario {
    val sessions = listOf(newSession(id = "1"), newSession(id = "2"))

    givenScenario {
      every { observeAllGrindsUseCase() } returns flow { emit(sessions) }
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.componentState) { stateList ->
      stateList.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(sessions),
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      verifyOrder { observeAllGrindsUseCase() }
      confirmVerified(observeAllGrindsUseCase, analytics)
    }
  }

  @Test
  fun `should session clicked`() = observerScenario {
    val clickedSession = newSession(id = "1")

    whenAction {
      target.interact(
        GrindsInteraction.OnGrindEvent(GrindAdapterConsumerEvent.SessionClicked(clickedSession))
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindsCommand.NavigateToSessionDetails(clickedSession),
    )

    thenAssert {
      coVerifyOrder {
        analytics.onSessionViewed(clickedSession)
      }
      confirmVerified(observeAllGrindsUseCase, analytics)
    }
  }
}