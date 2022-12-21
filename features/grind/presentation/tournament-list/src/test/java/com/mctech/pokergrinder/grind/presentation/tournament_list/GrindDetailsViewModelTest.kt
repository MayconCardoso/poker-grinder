package com.mctech.pokergrinder.grind.presentation.tournament_list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.usecase.GroupGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindDetailsViewModelTest : BaseViewModelTest() {
  private val observeSettingsUseCase = mockk<ObserveSettingsUseCase>(relaxed = true)
  private val registerTournamentUseCase = mockk<RegisterTournamentUseCase>(relaxed = true)
  private val groupGrindTournamentUseCase = mockk<GroupGrindTournamentUseCase>(relaxed = true)
  private val observeGrindTournamentUseCase = mockk<ObserveGrindTournamentUseCase>(relaxed = true)
  private val target = GrindDetailsViewModel(
    observeSettingsUseCase = observeSettingsUseCase,
    registerTournamentUseCase = registerTournamentUseCase,
    groupGrindTournamentUseCase = groupGrindTournamentUseCase,
    observeGrindTournamentUseCase = observeGrindTournamentUseCase,
  )

  @Test
  fun `should initialize component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.tournamentsState) { states ->
      states.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssert {
      assertThat(target.session).isNull()
      confirmAll()
    }
  }

  @Test
  fun `should load component data`() = observerScenario {
    val session = newSession(id = "1")

    whenAction {
      target.interact(GrindDetailsInteraction.ScreenFirstOpen(session))
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.tournamentsState) { states ->
      states.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      verifyOrder {
        observeGrindTournamentUseCase(session.id)
        observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)
      }
      confirmAll()
    }
  }


  @Test
  fun `should group tournaments`() = observerScenario {
    val session = newSession(id = "1")

    whenAction {
      target.interact(GrindDetailsInteraction.ScreenFirstOpen(session))
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.tournamentsState) { states ->
      states.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      verifyOrder {
        observeGrindTournamentUseCase(session.id)
        observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)
      }
      confirmAll()
    }
  }

  private fun confirmAll() {
    confirmVerified(
      observeSettingsUseCase,
      registerTournamentUseCase,
      groupGrindTournamentUseCase,
      observeSettingsUseCase
    )
  }
}