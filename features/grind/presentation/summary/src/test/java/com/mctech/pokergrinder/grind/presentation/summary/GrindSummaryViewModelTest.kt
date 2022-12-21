package com.mctech.pokergrinder.grind.presentation.summary

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentFlipsUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindUseCase
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newSessionFlip
import com.mctech.pokergrinder.grind.testing.newTournament
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindSummaryViewModelTest : BaseViewModelTest() {
  private val grindCase = mockk<ObserveGrindUseCase>(relaxed = true)
  private val grindTournamentCase = mockk<ObserveGrindTournamentUseCase>(relaxed = true)
  private val grindTournamentFlipCase = mockk<ObserveGrindTournamentFlipsUseCase>(relaxed = true)
  private val target = GrindSummaryViewModel(
    observeGrindUseCase = grindCase,
    observeGrindTournamentUseCase = grindTournamentCase,
    observeGrindTournamentFlipsUseCase = grindTournamentFlipCase,
  )

  @Test
  fun `should initialize component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.detailsState) { states ->
      states.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssertFlow(target.chartState) { states ->
      assertThat(states).isEqualTo(listOf(listOf<MoneyVariationEntry>()))
    }

    thenAssertFlow(target.flipState) { states ->
      assertThat(states).isEqualTo(listOf(listOf<SessionTournamentFlip>()))
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      confirmVerified(grindCase, grindTournamentCase, grindTournamentFlipCase)
    }
  }

  @Test
  fun `should load component data`() = observerScenario {
    val session = newSession(id = "1")
    val flips = listOf(newSessionFlip(id = "1"), newSessionFlip(id = "1"))
    val tournaments = listOf(newTournament(id = "1"), newTournament(id = "1"))

    givenScenario {
      every { grindCase(any()) } returns flow { emit(session) }
      every { grindTournamentCase(any()) } returns flow { emit(tournaments) }
      every { grindTournamentFlipCase(any()) } returns flow { emit(flips) }
    }

    whenAction {
      target.interact(GrindSummaryInteraction.ScreenFirstOpen(session))
    }

    thenAssertFlow(target.detailsState) { states ->
      states.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(session),
      )
    }

    thenAssertFlow(target.chartState) { states ->
      assertThat(states.last()).isEqualTo(flips.reversed().map { MoneyVariationEntry(0.0) })
    }

    thenAssertFlow(target.flipState) { states ->
      assertThat(states.last()).isEqualTo(flips)
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      coVerifyOrder {
        grindCase(session.id)
        grindTournamentCase(session.id)
        grindTournamentFlipCase(session.id)
      }
      confirmVerified(grindCase, grindTournamentCase, grindTournamentFlipCase)
    }
  }
}