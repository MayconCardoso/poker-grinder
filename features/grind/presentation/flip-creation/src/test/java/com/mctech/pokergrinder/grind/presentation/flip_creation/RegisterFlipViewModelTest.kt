package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentFlipUseCase
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newTournament
import com.mctech.pokergrinder.localization.R
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RegisterFlipViewModelTest : BaseViewModelTest() {
  private val observeGrindTournamentUseCase = mockk<ObserveGrindTournamentUseCase>(relaxed = true)
  private val registerTournamentFlipUseCase = mockk<RegisterTournamentFlipUseCase>(relaxed = true)
  private val target = RegisterFlipViewModel(
    observeGrindTournamentUseCase = observeGrindTournamentUseCase,
    registerTournamentFlipUseCase = registerTournamentFlipUseCase,
  )

  private val session = newSession(id = "1")
  private val tournaments = listOf(
    newTournament(id = "1", title = "Sit and Go $25"),
    newTournament(id = "2", title = "Sit and Go $25"),
    newTournament(id = "3", title = "Sit and Go $50"),
    newTournament(id = "4", title = "Sit and Go $50"),
  )

  @Test
  fun `should initialize component`() = observerScenario {
    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states).isEqualTo(listOf(RegisterFlipState()))
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertThat(target.session).isNull()
      assertThat(target.selectedTournament).isNull()
      assertThat(target.heroCards).isEmpty()
      assertThat(target.boardCards).isEmpty()
      assertThat(target.villainCards).isEmpty()
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  @Test
  fun `should load tournaments`() = observerScenario {
    givenScenario {
      every { observeGrindTournamentUseCase(session.id) } returns flow { emit(tournaments) }
    }

    whenAction {
      target.interact(RegisterFlipInteraction.ScreenFirstOpen(session))
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(
        RegisterFlipState(
          tournaments = tournaments.distinctBy { it.title },
          currentFlow = RegisterFlipFlow.TOURNAMENT_PICKER,
        )
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertThat(target.session).isEqualTo(session)
      assertThat(target.selectedTournament).isNull()
      assertThat(target.heroCards).isEmpty()
      assertThat(target.boardCards).isEmpty()
      assertThat(target.villainCards).isEmpty()
      verifyOrder {
        observeGrindTournamentUseCase(session.id)
      }
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  @Test
  fun `should select tournament`() = observerScenario {
    val selectedTournament = newTournament(id = "1")

    whenAction {
      target.interact(RegisterFlipInteraction.TournamentSelected(selectedTournament))
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(
        RegisterFlipState(
          message = R.string.select_hero_cards,
          currentFlow = RegisterFlipFlow.HERO_CARD_PICKER,
        )
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertThat(target.session).isNull()
      assertThat(target.selectedTournament).isEqualTo(selectedTournament)
      assertThat(target.heroCards).isEmpty()
      assertThat(target.boardCards).isEmpty()
      assertThat(target.villainCards).isEmpty()
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  @Test
  fun `should select first hero card`() {
    val cards = listOf(Card(suit = CardSuit.CLUBS, name = "A", value = 14))
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.HERO_CARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEqualTo(cards)
        assertThat(target.boardCards).isEmpty()
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should select second hero card`() = observerScenario {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.DIAMONDS, name = "A", value = 14),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.HERO_CARD_PICKER)
    val expectedState = RegisterFlipState(
      message = R.string.select_villain_cards,
      currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER,
      disableCards = cards
    )

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = expectedState,
      assertion = {
        assertThat(target.heroCards).isEqualTo(cards)
        assertThat(target.boardCards).isEmpty()
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should unselect hero card`() = unselectCardFlow(
    currentFlow = RegisterFlipFlow.HERO_CARD_PICKER,
  )

  @Test
  fun `should select first villain card`() {
    val cards = listOf(Card(suit = CardSuit.CLUBS, name = "A", value = 14))
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEmpty()
        assertThat(target.villainCards).isEqualTo(cards)
      }
    )
  }

  @Test
  fun `should select second villain card`() {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.DIAMONDS, name = "A", value = 14),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER)
    val expectedState = RegisterFlipState(
      message = R.string.select_board_cards,
      currentFlow = RegisterFlipFlow.BOARD_PICKER,
      disableCards = cards
    )

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = expectedState,
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEmpty()
        assertThat(target.villainCards).isEqualTo(cards)
      }
    )
  }

  @Test
  fun `should unselect villain card`() = unselectCardFlow(
    currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER,
  )

  @Test
  fun `should select first board card`() {
    val cards = listOf(Card(suit = CardSuit.CLUBS, name = "A", value = 14))
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.BOARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEqualTo(cards)
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should select second board card`() {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.CLUBS, name = "K", value = 13),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.BOARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEqualTo(cards)
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should select third board card`() {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.CLUBS, name = "K", value = 13),
      Card(suit = CardSuit.CLUBS, name = "Q", value = 12),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.BOARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEqualTo(cards)
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should select forth board card`() {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.CLUBS, name = "K", value = 13),
      Card(suit = CardSuit.CLUBS, name = "Q", value = 12),
      Card(suit = CardSuit.CLUBS, name = "J", value = 11),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.BOARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = initialState.copy(disableCards = cards),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEqualTo(cards)
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should select fifth board card`() {
    val cards = listOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.CLUBS, name = "K", value = 13),
      Card(suit = CardSuit.CLUBS, name = "Q", value = 12),
      Card(suit = CardSuit.CLUBS, name = "J", value = 11),
      Card(suit = CardSuit.CLUBS, name = "T", value = 10),
    )
    val initialState = RegisterFlipState(currentFlow = RegisterFlipFlow.BOARD_PICKER)

    cardSelectedFlow(
      cards = cards,
      initialState = initialState,
      expectedState = RegisterFlipState(
        message = R.string.select_the_winner,
        currentFlow = RegisterFlipFlow.WHO_WON,
      ),
      assertion = {
        assertThat(target.heroCards).isEmpty()
        assertThat(target.boardCards).isEqualTo(cards)
        assertThat(target.villainCards).isEmpty()
      }
    )
  }

  @Test
  fun `should unselect board card`() = unselectCardFlow(
    currentFlow = RegisterFlipFlow.BOARD_PICKER,
  )

  @Test
  fun `should navigate back when on winner screen`() = navigateBackFlow(
    currentState = RegisterFlipState(
      message = R.string.select_the_winner,
      currentFlow = RegisterFlipFlow.WHO_WON,
    ),
    expectedState = RegisterFlipState(
      message = R.string.select_board_cards,
      currentFlow = RegisterFlipFlow.BOARD_PICKER,
    ),
  )

  @Test
  fun `should navigate back when on board screen`() = navigateBackFlow(
    currentState = RegisterFlipState(
      message = R.string.select_board_cards,
      currentFlow = RegisterFlipFlow.BOARD_PICKER,
    ),
    expectedState = RegisterFlipState(
      message = R.string.select_villain_cards,
      currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER,
    ),
  )

  @Test
  fun `should navigate back when on villain screen`() = navigateBackFlow(
    currentState = RegisterFlipState(
      message = R.string.select_villain_cards,
      currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER,
    ),
    expectedState = RegisterFlipState(
      message = R.string.select_hero_cards,
      currentFlow = RegisterFlipFlow.HERO_CARD_PICKER,
    ),
  )

  @Test
  fun `should navigate back when on hero screen`() = navigateBackFlow(
    currentState = RegisterFlipState(
      message = R.string.select_hero_cards,
      currentFlow = RegisterFlipFlow.HERO_CARD_PICKER,
    ),
    expectedState = RegisterFlipState(
      message = R.string.select_the_tournament,
      currentFlow = RegisterFlipFlow.TOURNAMENT_PICKER,
    ),
  )

  @Test
  fun `should create flip when hero win`() = winnerSelectionFlow(
    interaction = RegisterFlipInteraction.HeroWonFlip,
    hasHeroWon = true,
  )

  @Test
  fun `should create flip when villain win`() = winnerSelectionFlow(
    interaction = RegisterFlipInteraction.VillainWonFlip,
    hasHeroWon = false,
  )

  private fun cardSelectedFlow(
    cards: List<Card>,
    initialState: RegisterFlipState,
    expectedState: RegisterFlipState,
    assertion: () -> Unit,
  ) = observerScenario {

    givenScenario {
      startComponent(initialState)
    }

    whenAction {
      cards.forEach { card ->
        target.interact(RegisterFlipInteraction.CardSelected(card))
      }
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(expectedState.copy(disableCards = cards))
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertion()
      assertThat(target.session).isNull()
      assertThat(target.selectedTournament).isNull()
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  private fun unselectCardFlow(currentFlow: RegisterFlipFlow) = observerScenario {
    val card = Card(suit = CardSuit.CLUBS, name = "A", value = 14)
    val initialState = RegisterFlipState(currentFlow = currentFlow, disableCards = listOf(card))
    val expectedState = initialState.copy(disableCards = listOf())

    givenScenario {
      startComponent(initialState)
    }

    whenAction {
      target.interact(RegisterFlipInteraction.CardUnselected(card))
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(expectedState)
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertThat(target.session).isNull()
      assertThat(target.selectedTournament).isNull()
      assertThat(target.heroCards).isEmpty()
      assertThat(target.boardCards).isEmpty()
      assertThat(target.villainCards).isEmpty()
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  private fun navigateBackFlow(
    currentState: RegisterFlipState,
    expectedState: RegisterFlipState,
  ) = observerScenario {

    givenScenario {
      startComponent(currentState)
    }

    whenAction {
      target.interact(RegisterFlipInteraction.OnBackPressed)
    }

    thenAssertFlow(target.componentState) { states ->
      assertThat(states.last()).isEqualTo(expectedState)
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssert {
      assertThat(target.session).isNull()
      assertThat(target.selectedTournament).isNull()
      assertThat(target.heroCards).isEmpty()
      assertThat(target.boardCards).isEmpty()
      assertThat(target.villainCards).isEmpty()
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  private fun winnerSelectionFlow(
    interaction: RegisterFlipInteraction,
    hasHeroWon: Boolean,
  ) = observerScenario{
    val session = newSession(id = "100")
    val tournament = newTournament(title = "Bounty Builder $11")
    val heroCards = mutableListOf(
      Card(suit = CardSuit.CLUBS, name = "A", value = 14),
      Card(suit = CardSuit.CLUBS, name = "K", value = 13),
    )
    val villainCards = mutableListOf(
      Card(suit = CardSuit.DIAMONDS, name = "A", value = 14),
      Card(suit = CardSuit.DIAMONDS, name = "K", value = 13),
    )
    val boardCards = mutableListOf(
      Card(suit = CardSuit.HEARTS, name = "2", value = 2),
      Card(suit = CardSuit.HEARTS, name = "3", value = 3),
      Card(suit = CardSuit.HEARTS, name = "4", value = 4),
      Card(suit = CardSuit.HEARTS, name = "5", value = 5),
      Card(suit = CardSuit.HEARTS, name = "6", value = 6),
    )

    givenScenario {
      target.session = session
      target.selectedTournament = tournament
      target.heroCards = heroCards
      target.villainCards = villainCards
      target.boardCards = boardCards
    }

    whenAction {
      target.interact(interaction)
    }

    thenAssert {
      coVerifyOrder {
        registerTournamentFlipUseCase(
          session = session,
          title = "Bounty Builder $11",
          heroCards = heroCards,
          villainCards = villainCards,
          boardCards = boardCards,
          heroWon = hasHeroWon,
        )
      }
      confirmVerified(observeGrindTournamentUseCase, registerTournamentFlipUseCase)
    }
  }

  private fun startComponent(state: RegisterFlipState) {
    (target.componentState as MutableStateFlow).value = state
  }
}