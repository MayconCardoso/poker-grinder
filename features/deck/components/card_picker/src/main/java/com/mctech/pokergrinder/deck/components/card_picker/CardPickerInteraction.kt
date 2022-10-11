package com.mctech.pokergrinder.deck.components.card_picker

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.domain.CardSuit

internal sealed class CardPickerInteraction : UserInteraction {
  data class DeckSuitChanged(val suit: CardSuit) : CardPickerInteraction()
}