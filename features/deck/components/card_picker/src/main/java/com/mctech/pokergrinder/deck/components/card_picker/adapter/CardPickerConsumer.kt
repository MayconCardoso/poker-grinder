package com.mctech.pokergrinder.deck.components.card_picker.adapter

internal interface CardPickerConsumer {
  fun consume(event: CardPickerConsumerEvent)
}