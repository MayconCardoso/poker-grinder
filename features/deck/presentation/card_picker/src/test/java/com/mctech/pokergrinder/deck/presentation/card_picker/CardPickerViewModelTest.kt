package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.architecture_testing.threading.TestCoroutineDispatcher
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit
import com.mctech.pokergrinder.deck.domain.Deck
import com.mctech.pokergrinder.deck.presentation.card_picker.adapter.CardPickerConsumerEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class CardPickerViewModelTest : BaseViewModelTest() {
  private val target = CardPickerViewModel(
    dispatchers = TestCoroutineDispatcher
  )

  @Test
  fun `should initialize component`() = observerScenario {
    val initialState = Deck.cards
      .filter { it.suit == CardSuit.CLUBS }
      .map { CardPickerState(card = it) }

    whenAction {
      target.initialize()
    }

    thenAssertFlow(target.state) { states ->
      assertThat(states).containsExactly(listOf(), initialState)
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      assertThat(target.disabledCards).isEmpty()
      assertThat(target.selectedSuit).isEqualTo(CardSuit.CLUBS)
    }
  }

  @Test
  fun `should change cards suit`() = observerScenario {
    val expectedState = Deck.cards
      .filter { it.suit == CardSuit.DIAMONDS }
      .map { CardPickerState(card = it) }

    whenAction {
      target.interact(CardPickerInteraction.DeckSuitChanged(CardSuit.DIAMONDS))
    }

    thenAssertFlow(target.state) { states ->
      assertThat(states).containsExactly(
        listOf(),
        expectedState
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      assertThat(target.disabledCards).isEmpty()
      assertThat(target.selectedSuit).isEqualTo(CardSuit.DIAMONDS)
    }
  }

  @Test
  fun `should disable cards`() = observerScenario {
    val disabledCards = Deck.cards.filter { it.name == "A" }
    val expectedState = Deck.cards
      .filter { it.suit == CardSuit.CLUBS }
      .map { CardPickerState(card = it, disabled = disabledCards.contains(it)) }

    whenAction {
      target.interact(CardPickerInteraction.DisableCards(disabledCards))
    }

    thenAssertFlow(target.state) { states ->
      assertThat(states).containsExactly(
        listOf(),
        expectedState
      )
    }

    thenAssertLiveDataFlowIsEmpty(target.commandObservable)

    thenAssert {
      assertThat(target.disabledCards).isEqualTo(disabledCards)
      assertThat(target.selectedSuit).isEqualTo(CardSuit.CLUBS)
    }
  }

  @Test
  fun `should unselect a selected card`() = observerScenario {
    val card = Card(value = 14, name = "A", suit = CardSuit.CLUBS)
    val state = CardPickerState(
      card = card,
      disabled = true,
    )

    whenAction {
      target.interact(
        CardPickerInteraction.CardEvent(CardPickerConsumerEvent.CardClicked(state))
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).containsExactly(CardPickerCommand.CardUnselected(card))
    }
  }

  @Test
  fun `should select a unselect card`() = observerScenario {
    val card = Card(value = 14, name = "A", suit = CardSuit.CLUBS)
    val state = CardPickerState(
      card = card,
      disabled = false,
    )

    whenAction {
      target.interact(
        CardPickerInteraction.CardEvent(CardPickerConsumerEvent.CardClicked(state))
      )
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).containsExactly(CardPickerCommand.CardSelected(card))
    }
  }
}