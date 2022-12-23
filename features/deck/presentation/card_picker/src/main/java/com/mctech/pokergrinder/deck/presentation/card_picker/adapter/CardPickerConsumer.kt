package com.mctech.pokergrinder.deck.presentation.card_picker.adapter

/**
 * Card picker event consumer that will be notified when an [CardPickerConsumerEvent] is sent.
 */
internal interface CardPickerConsumer {

  /**
   * Called to consume a new event.
   */
  fun consume(event: CardPickerConsumerEvent)
}