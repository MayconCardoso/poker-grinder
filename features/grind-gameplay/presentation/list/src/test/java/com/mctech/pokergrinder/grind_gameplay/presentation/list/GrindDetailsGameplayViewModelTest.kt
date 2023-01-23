package com.mctech.pokergrinder.grind_gameplay.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind_gameplay.domain.usecase.ObserveGrindTournamentFlipsUseCase
import com.mctech.pokergrinder.grind_gameplay.testing.newSessionFlip
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindDetailsGameplayViewModelTest : BaseViewModelTest() {
  private val observeGrindUseCase = mockk<ObserveGrindTournamentFlipsUseCase>(relaxed = true)
  private val target =
    GrindDetailsGameplayViewModel(
      observeGrindTournamentFlipsUseCase = observeGrindUseCase,
    )

  @Test
  fun `should initialize component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.state) { stateList ->
      stateList.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      confirmVerified(observeGrindUseCase)
    }
  }

  @Test
  fun `should load flips for the session`() = observerScenario {
    val session = newSession()
    val flips = listOf(
      newSessionFlip(id = "1"),
      newSessionFlip(id = "2"),
    )
    val flow = flow {
      emit(flips)
    }

    givenScenario {
      every { observeGrindUseCase(session.id) } returns flow
    }

    whenAction {
      target.interact(GrindDetailsGameplayInteraction.ScreenFirstOpen(session))
    }

    thenAssertFlow(target.state) { stateList ->
      stateList.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(flips),
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      verifyOrder { observeGrindUseCase(session.id) }
      confirmVerified(observeGrindUseCase)
    }
  }
}