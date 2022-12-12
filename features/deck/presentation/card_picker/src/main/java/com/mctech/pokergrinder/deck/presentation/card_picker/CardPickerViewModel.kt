package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.threading.CoroutineDispatchers
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.deck.presentation.card_picker.adapter.CardPickerConsumerEvent
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit
import com.mctech.pokergrinder.deck.domain.Deck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
public class CardPickerViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds all disabled cards.
   */
  private var disabledCards = mutableListOf<Card>()

  /**
   * Holds the selected suit.
   */
  private var selectedSuit: CardSuit = CardSuit.CLUBS

  /**
   * Holds the screen state.s
   */
  private val _state by lazy { MutableStateFlow(listOf<CardPickerState>()) }
  internal val state: StateFlow<List<CardPickerState>> by lazy { _state }

  // endregion

  // region Initialization

  override suspend fun initializeComponents() {
    _state.value = Deck.cards
      .filter { it.suit == CardSuit.CLUBS }
      .map { CardPickerState(card = it) }
  }

  // endregion

  // region Interactions

  @OnInteraction(CardPickerInteraction.DeckSuitChanged::class)
  private suspend fun onCardSuitChanged(
    interaction: CardPickerInteraction.DeckSuitChanged,
  ) = withContext(dispatchers.default) {
    // Holds the new selected suit.
    selectedSuit = interaction.suit

    // Emit changes.
    emitCardChanges()
  }

  @OnInteraction(CardPickerInteraction.CardEvent::class)
  private suspend fun onCardSuitChanged(interaction: CardPickerInteraction.CardEvent) {
    when (interaction.event) {
      is CardPickerConsumerEvent.CardClicked -> {
        handleCardClicked(interaction.event.card)
      }
    }
  }

  @OnInteraction(CardPickerInteraction.DisableCards::class)
  private suspend fun disableCardsInteraction(
    interaction: CardPickerInteraction.DisableCards,
  ) = withContext(dispatchers.default) {
    // Hold disable cards.
    disabledCards.clear()
    disabledCards.addAll(interaction.cards)

    // Emit state with changes.
    emitCardChanges()
  }

  private suspend fun handleCardClicked(state: CardPickerState) {
    if (state.disabled) {
      sendCommand(CardPickerCommand.CardUnselected(state.card))
    } else {
      sendCommand(CardPickerCommand.CardSelected(state.card))
    }
  }

  private suspend fun emitCardChanges() {
    val state = Deck.cards
      .filter {
        it.suit == selectedSuit
      }
      .map {
        CardPickerState(
          card = it,
          disabled = disabledCards.contains(it),
        )
      }

    withContext(dispatchers.main) {
      _state.value = state
    }
  }
  // endregion
}