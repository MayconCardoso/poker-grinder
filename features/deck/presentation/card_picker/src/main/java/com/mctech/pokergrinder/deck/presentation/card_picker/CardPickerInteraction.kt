package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.presentation.card_picker.adapter.CardPickerConsumerEvent
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit

public sealed class CardPickerInteraction : UserInteraction {
  internal data class DeckSuitChanged(val suit: CardSuit) : CardPickerInteraction()
  internal data class CardEvent(val event: CardPickerConsumerEvent) : CardPickerInteraction()
  public data class DisableCards(val cards: List<Card>): CardPickerInteraction()
}