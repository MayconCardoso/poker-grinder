package com.mctech.pokergrinder.deck.presentation.card_picker.adapter

internal interface CardPickerConsumer {
  fun consume(event: CardPickerConsumerEvent)
}