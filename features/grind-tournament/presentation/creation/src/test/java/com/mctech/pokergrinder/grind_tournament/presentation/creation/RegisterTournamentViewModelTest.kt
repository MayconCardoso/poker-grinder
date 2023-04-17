package com.mctech.pokergrinder.grind_tournament.presentation.creation

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentAnalytics
import com.mctech.pokergrinder.grind_tournament.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.UpdatesTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.usecase.LoadTournamentUseCase
import com.mctech.pokergrinder.tournament.domain.usecase.SavesTournamentUseCase
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RegisterTournamentViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<GrindTournamentAnalytics>(relaxed = true)
  private val updatesTournamentUseCase = mockk<UpdatesTournamentUseCase>(relaxed = true)
  private val registerTournamentUseCase = mockk<RegisterTournamentUseCase>(relaxed = true)
  private val loadTournamentUseCase = mockk<LoadTournamentUseCase>(relaxed = true)
  private val savesTournamentUseCase = mockk<SavesTournamentUseCase>(relaxed = true)
  private val target = RegisterTournamentViewModel(
    analytics = analytics,
    updatesTournamentUseCase = updatesTournamentUseCase,
    registerTournamentUseCase = registerTournamentUseCase,
    loadTournamentUseCase = loadTournamentUseCase,
    savesTournamentUseCase = savesTournamentUseCase,
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
      confirmAll()
    }
  }

  @Test
  fun `should initialize tournament`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1")

    whenAction {
      target.interact(
        RegisterTournamentInteraction.ScreenFirstOpen(
          session = session,
          tournament = tournament
        )
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(tournament)
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      confirmAll()
    }
  }

  @Test
  fun `should create a new tournament instance`() = observerScenario {
    val session = newSession(id = "1")

    givenScenario {
      target.session = session
      coEvery { loadTournamentUseCase(title = "Hey") } returns null
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
      val tournamentSlot = slot<Tournament>()

      coVerifyOrder {
        registerTournamentUseCase(session = session, title = "Hey", buyIn = 1.0)
        loadTournamentUseCase(title = "Hey")
        savesTournamentUseCase(capture(tournamentSlot))
      }

      assertThat(tournamentSlot.captured.buyIn).isEqualTo(1.0F)
      assertThat(tournamentSlot.captured.title).isEqualTo("Hey")

      confirmAll()
    }
  }


  @Test
  fun `should update existent tournament`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", buyIn = 10.0, profit = 50.0)
    val expected = tournament.copy(
      title = "Hey", buyIn = 1.0, profit = 7.0
    )
    val savedTournament = mockk<Tournament>(relaxed = true)

    givenScenario {
      target.session = session
      coEvery { loadTournamentUseCase(title = "Hey") } returns savedTournament
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
        loadTournamentUseCase(title = "Hey")
      }
      confirmAll()
    }
  }

  private fun confirmAll() {
    confirmVerified(
      updatesTournamentUseCase,
      registerTournamentUseCase,
      loadTournamentUseCase,
      savesTournamentUseCase,
    )
  }
}