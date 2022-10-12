package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.deck.domain.Card

public sealed class CardPickerCommand : ViewCommand {
  public data class CardSelected(val card: Card) : CardPickerCommand()
  public data class CardUnselected(val card: Card) : CardPickerCommand()
}