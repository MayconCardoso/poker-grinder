package com.mctech.pokergrinder.deck.components.card_picker

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.deck.domain.Deck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class CardPickerViewModel @Inject constructor() : BaseViewModel() {

  private val _state by lazy { MutableStateFlow(Deck.cards.map { CardPickerState(card = it) }) }
  val state: StateFlow<List<CardPickerState>> by lazy { _state }

}