package com.mctech.pokergrinder.deck.components.card_picker.adapter

import com.mctech.pokergrinder.deck.components.card_picker.CardPickerState

internal sealed class CardPickerConsumerEvent {
  data class CardClicked(val card: CardPickerState) : CardPickerConsumerEvent()
}