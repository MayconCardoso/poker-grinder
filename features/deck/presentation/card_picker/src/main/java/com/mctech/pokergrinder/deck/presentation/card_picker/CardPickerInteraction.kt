package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.presentation.card_picker.adapter.CardPickerConsumerEvent
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit

/**
 * Holds the available command for the feature
 */
public sealed class CardPickerInteraction : UserInteraction {

  /**
   * Called to indicate the suit has been changed.
   */
  internal data class DeckSuitChanged(val suit: CardSuit) : CardPickerInteraction()

  /**
   * Called to indicate a card event has been thrown.
   */
  internal data class CardEvent(val event: CardPickerConsumerEvent) : CardPickerInteraction()

  /**
   * Called to disable given [cards].
   */
  public data class DisableCards(val cards: List<Card>): CardPickerInteraction()
}