package com.mctech.pokergrinder.grind_tournament.presentation.list

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentAnalytics
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.domain.usecase.GroupGrindTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.domain.usecase.RegisterTournamentUseCase
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import io.mockk.*
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindDetailsViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<GrindTournamentAnalytics>(relaxed = true)
  private val observeSettingsUseCase = mockk<ObserveSettingsUseCase>(relaxed = true)
  private val registerTournamentUseCase = mockk<RegisterTournamentUseCase>(relaxed = true)
  private val groupGrindTournamentUseCase = mockk<GroupGrindTournamentUseCase>(relaxed = true)
  private val observeGrindTournamentUseCase = mockk<ObserveGrindTournamentUseCase>(relaxed = true)
  private val target = GrindDetailsViewModel(
    analytics = analytics,
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
      assertThat(target.originalTemplateList).isEqualTo(listOf<SessionTournament>())
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
      assertThat(target.originalTemplateList).isEqualTo(listOf<SessionTournament>())
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
    val settings = SettingsAvailable.GROUP_TOURNAMENTS
    val tournaments = listOf(
      newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
      newTournament(id = "2", title = "Sit and Go $10", buyIn = 10.0, profit = 28.50),
      newTournament(id = "3", title = "Sit and Go $25", buyIn = 25.0, profit = 0.0),
      newTournament(id = "4", title = "Sit and Go $25", buyIn = 25.0, profit = 180.0),
    )
    val groupedTournaments = listOf(
      newTournament(id = "", title = "Sit and Go $10", buyIn = 20.0, profit = 28.50),
      newTournament(id = "", title = "Sit and Go $25", buyIn = 50.0, profit = 180.0),
    )

    givenScenario {
      every { observeSettingsUseCase(settings) } returns flow { emit(newEnabledSettings()) }
      every { observeGrindTournamentUseCase(session.id) } returns flow { emit(tournaments) }
      every { groupGrindTournamentUseCase(tournaments) } returns groupedTournaments
    }

    whenAction {
      target.interact(GrindDetailsInteraction.ScreenFirstOpen(session))
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.tournamentsState) { states ->
      states.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(groupedTournaments),
      )
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      assertThat(target.originalTemplateList).isEqualTo(tournaments)
      verifyOrder {
        observeGrindTournamentUseCase(session.id)
        observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)
        groupGrindTournamentUseCase(tournaments)
      }
      confirmAll()
    }
  }

  @Test
  fun `should not group tournaments`() = observerScenario {
    val session = newSession(id = "1")
    val settings = SettingsAvailable.GROUP_TOURNAMENTS
    val tournaments = listOf(
      newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
      newTournament(id = "2", title = "Sit and Go $10", buyIn = 10.0, profit = 28.50),
      newTournament(id = "3", title = "Sit and Go $25", buyIn = 25.0, profit = 0.0),
      newTournament(id = "4", title = "Sit and Go $25", buyIn = 25.0, profit = 180.0),
    )

    givenScenario {
      every { observeSettingsUseCase(settings) } returns flow { emit(newDisabledSettings()) }
      every { observeGrindTournamentUseCase(session.id) } returns flow { emit(tournaments) }
    }

    whenAction {
      target.interact(GrindDetailsInteraction.ScreenFirstOpen(session))
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssertFlow(target.tournamentsState) { states ->
      states.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(tournaments),
      )
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      assertThat(target.originalTemplateList).isEqualTo(tournaments)
      verifyOrder {
        observeGrindTournamentUseCase(session.id)
        observeSettingsUseCase(SettingsAvailable.GROUP_TOURNAMENTS)
      }
      confirmAll()
    }
  }

  @Test
  fun `should open tournament register`() = observerScenario {
    val session = newSession(id = "1")

    givenScenario {
      target.session = session
    }

    whenAction {
      target.interact(GrindDetailsInteraction.RegisterTournamentClicked)
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindDetailsCommand.GoToTournamentEditor(
        session = session,
        sessionTournament = null,
      )
    )

    thenAssert {
      confirmAll()
    }
  }

  @Test
  fun `should open tournament edit when clicked item is not grouped`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", isGrouped = false)
    val tournamentEvent = com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent.TournamentClicked(tournament)

    givenScenario {
      target.session = session
    }

    whenAction {
      target.interact(GrindDetailsInteraction.OnTournamentEvent(tournamentEvent))
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindDetailsCommand.GoToTournamentEditor(
        session = session,
        sessionTournament = tournament,
      )
    )

    thenAssert {
      confirmAll()
    }
  }

  @Test
  fun `should open tournament selection when clicked item is grouped`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", isGrouped = true, title = "Sit and Go $10")
    val tournamentEvent = com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent.TournamentClicked(tournament)

    givenScenario {
      target.session = session
      target.originalTemplateList = listOf(
        newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
        newTournament(id = "2", title = "Sit and Go $10", buyIn = 10.0, profit = 28.50),
        newTournament(id = "3", title = "Sit and Go $25", buyIn = 25.0, profit = 0.0),
        newTournament(id = "4", title = "Sit and Go $25", buyIn = 25.0, profit = 180.0),
      )
    }

    whenAction {
      target.interact(GrindDetailsInteraction.OnTournamentEvent(tournamentEvent))
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindDetailsCommand.ShowTournamentSelection(
        options = listOf(
          newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
          newTournament(id = "2", title = "Sit and Go $10", buyIn = 10.0, profit = 28.50),
        )
      )
    )

    thenAssert {
      confirmAll()
    }
  }

  @Test
  fun `should duplicate tournament`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", title = "Sit and Go $10")
    val tournamentEvent = com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent.DuplicateClicked(tournament)

    givenScenario {
      target.session = session
      target.originalTemplateList = listOf(
        newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
        newTournament(id = "4", title = "Sit and Go $25", buyIn = 25.0, profit = 180.0),
      )
    }

    whenAction {
      target.interact(GrindDetailsInteraction.OnTournamentEvent(tournamentEvent))
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      coVerifyOrder {
        registerTournamentUseCase(
          session = session,
          title = "Sit and Go $10",
          buyIn = 10.0
        )
      }
      confirmAll()
    }
  }

  @Test
  fun `should not duplicate tournament when out of balance`() = observerScenario {
    val session = newSession(id = "1")
    val tournament = newTournament(id = "1", title = "Sit and Go $10")
    val tournamentEvent = com.mctech.pokergrinder.grind_tournament.presentation.list.adapter.GrindDetailsConsumerEvent.DuplicateClicked(tournament)

    givenScenario {
      target.session = session
      target.originalTemplateList = listOf(
        newTournament(id = "1", title = "Sit and Go $10", buyIn = 10.0, profit = 0.0),
        newTournament(id = "4", title = "Sit and Go $25", buyIn = 25.0, profit = 180.0),
      )
      coEvery {
        registerTournamentUseCase(any(), any(), any())
      } throws BankrollException.InsufficientBalance
    }

    whenAction {
      target.interact(GrindDetailsInteraction.OnTournamentEvent(tournamentEvent))
    }

    thenAssertLiveDataContainsExactly(
      target.commandObservable,
      GrindDetailsCommand.InsufficientBalanceError
    )

    thenAssert {
      coVerifyOrder {
        registerTournamentUseCase(
          session = session,
          title = "Sit and Go $10",
          buyIn = 10.0
        )
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

  private fun newEnabledSettings() = Settings(key = "", value = "true", title = "")

  private fun newDisabledSettings() = Settings(key = "", value = "false", title = "")
}