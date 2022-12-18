package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.deck.domain.Card

/**
 * Holds the available command for the feature
 */
public sealed class CardPickerCommand : ViewCommand {

  /**
   * Used to mark a [card] as selected.
   */
  public data class CardSelected(val card: Card) : CardPickerCommand()

  /**
   * Used to mark a [card] as unselected.
   */
  public data class CardUnselected(val card: Card) : CardPickerCommand()
}