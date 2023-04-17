package com.mctech.pokergrinder.ranges.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.ranges.domain.RangeAnalytics
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveAllRangesUseCase
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangeAdapterConsumerEvent
import com.mctech.pokergrinder.ranges.testing.newRange
import io.mockk.*
import kotlinx.coroutines.flow.flow
import org.junit.Test

class RangesViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<RangeAnalytics>(relaxed = true)
  private val observeAllRangesUseCase = mockk<ObserveAllRangesUseCase>(relaxed = true)
  private val target = RangesViewModel(
    analytics = analytics,
    observeAllRangesUseCase = observeAllRangesUseCase,
  )

  @Test
  fun `should initialize component`() = TestObserverScenario.observerScenario {
    val ranges = listOf(newRange(id = "1"), newRange(id = "2"))

    givenScenario {
      every { observeAllRangesUseCase() } returns flow { emit(ranges) }
    }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.componentState) { stateList ->
      stateList.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(ranges),
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      verifyOrder { observeAllRangesUseCase() }
      confirmVerified(observeAllRangesUseCase, analytics)
    }
  }

  @Test
  fun `should load flips for the session`() = TestObserverScenario.observerScenario {
    val clickedRange = newRange(id = "1")

    whenAction {
      target.interact(
        RangesInteraction.OnRangeEvent(RangeAdapterConsumerEvent.Clicked(clickedRange))
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      RangesCommand.NavigateToViewer(clickedRange),
    )

    thenAssert {
      coVerifyOrder {
        analytics.onRangeViewed(clickedRange)
      }
      confirmVerified(observeAllRangesUseCase, analytics)
    }
  }
}