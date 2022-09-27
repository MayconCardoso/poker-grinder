package com.mctech.pokergrinder.deck.components.card_picker

import com.mctech.pokergrinder.deck.domain.Card

public data class CardPickerState(
  val card: Card,
  val disabled: Boolean = false,
)