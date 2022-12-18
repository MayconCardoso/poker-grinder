package com.mctech.pokergrinder.deck.presentation.card_picker.adapter

import com.mctech.pokergrinder.deck.presentation.card_picker.CardPickerState

/**
 * Describe a card picker event sent by user.
 */
internal sealed class CardPickerConsumerEvent {

  /**
   * Called to indicate a [card] has been clicked.
   */
  data class CardClicked(val card: CardPickerState) : CardPickerConsumerEvent()
}