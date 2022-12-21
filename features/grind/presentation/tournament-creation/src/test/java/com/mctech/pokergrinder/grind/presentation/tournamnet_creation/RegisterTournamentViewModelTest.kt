package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.UpdatesTournamentUseCase
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newTournament
import io.mockk.confirmVerified
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RegisterTournamentViewModelTest: BaseViewModelTest() {
  private val updatesTournamentUseCase = mockk<UpdatesTournamentUseCase>(relaxed = true)
  private val registerTournamentUseCase = mockk<RegisterTournamentUseCase>(relaxed = true)
  private val target = RegisterTournamentViewModel(
    updatesTournamentUseCase = updatesTournamentUseCase,
    registerTournamentUseCase = registerTournamentUseCase,
  )

  @Test
  fun `should initialize component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isNull()
    }

    thenAssert {
      assertThat(target.session).isNull()
      confirmVerified(updatesTournamentUseCase, registerTournamentUseCase)
    }
  }

  @Test
  fun `should initialize tournament`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1")

    whenAction {
      target.interact(
        RegisterTournamentInteraction.ScreenFirstOpen(session = session, tournament = tournament)
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(tournament)
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      confirmVerified(updatesTournamentUseCase, registerTournamentUseCase)
    }
  }
}