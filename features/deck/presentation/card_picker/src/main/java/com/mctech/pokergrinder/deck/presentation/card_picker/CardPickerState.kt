package com.mctech.pokergrinder.deck.presentation.card_picker

import com.mctech.pokergrinder.deck.domain.Card

/**
 * Holds a card state on the card picker component.
 *
 * @property card card being rendered.
 * @property disabled indicates if the card is disabled and cannot be selected.
 */
public data class CardPickerState(
  val card: Card,
  val disabled: Boolean = false,
)