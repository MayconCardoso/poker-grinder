package com.mctech.pokergrinder.deck.components.card_picker

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.deck.domain.CardSuit
import com.mctech.pokergrinder.deck.domain.Deck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class CardPickerViewModel @Inject constructor() : BaseViewModel() {

  private val _state by lazy { MutableStateFlow(listOf<CardPickerState>()) }
  val state: StateFlow<List<CardPickerState>> by lazy { _state }

  override suspend fun initializeComponents() {
    // Start with club cards
    _state.value = Deck.cards
      .filter { it.suit == CardSuit.CLUBS }
      .map { CardPickerState(card = it) }
  }

  @OnInteraction(CardPickerInteraction.DeckSuitChanged::class)
  private fun onCardSuitChanged(interaction: CardPickerInteraction.DeckSuitChanged) {
    _state.value = Deck.cards
      .filter { it.suit == interaction.suit }
      .map { CardPickerState(card = it) }
  }

}