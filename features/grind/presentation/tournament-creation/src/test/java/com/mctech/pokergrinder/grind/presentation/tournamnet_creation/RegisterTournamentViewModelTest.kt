package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.UpdatesTournamentUseCase
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newTournament
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RegisterTournamentViewModelTest : BaseViewModelTest() {
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

  @Test
  fun `should create a new tournament instance`() = observerScenario {
    val session = newSession(id = "1")

    givenScenario {
      target.session = session
    }

    whenAction {
      target.interact(
        RegisterTournamentInteraction.SaveTournament(
          title = "Hey",
          buyIn = 1.0,
          profit = 2.0,
          addNewProfit = 5.0,
        )
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      RegisterTournamentCommand.CloseScreen,
    )

    thenAssert {
      coVerifyOrder {
        registerTournamentUseCase(session = session, title = "Hey", buyIn = 1.0)
      }
      confirmVerified(updatesTournamentUseCase, registerTournamentUseCase)
    }
  }


  @Test
  fun `should update existent tournament`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", buyIn = 10.0, profit = 50.0)
    val expected = tournament.copy(
      title = "Hey", buyIn = 1.0, profit = 7.0
    )

    givenScenario {
      target.session = session
    }

    whenAction {
      target.interact(
        RegisterTournamentInteraction.ScreenFirstOpen(session = session, tournament = tournament)
      )

      target.interact(
        RegisterTournamentInteraction.SaveTournament(
          title = "Hey",
          buyIn = 1.0,
          profit = 2.0,
          addNewProfit = 5.0,
        )
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      RegisterTournamentCommand.CloseScreen,
    )

    thenAssert {
      coVerifyOrder {
        updatesTournamentUseCase(expected)
      }
      confirmVerified(updatesTournamentUseCase, registerTournamentUseCase)
    }
  }

  @Test
  fun `should show error when out of balance`() = observerScenario {
    val session = newSession(id = "1")

    givenScenario {
      target.session = session
      coEvery {
        registerTournamentUseCase(any(), any(), any())
      } throws BankrollException.InsufficientBalance
    }

    whenAction {
      target.interact(
        RegisterTournamentInteraction.SaveTournament(
          title = "Hey",
          buyIn = 1.0,
          profit = 2.0,
          addNewProfit = 5.0,
        )
      )
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      RegisterTournamentCommand.InsufficientBalanceError,
    )

    thenAssert {
      coVerifyOrder {
        registerTournamentUseCase(session = session, title = "Hey", buyIn = 1.0)
      }
      confirmVerified(updatesTournamentUseCase, registerTournamentUseCase)
    }
  }
}