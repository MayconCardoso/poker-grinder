package com.mctech.pokergrinder.deck.components.card_picker

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.deck.components.card_picker.databinding.FragmentCardPickerBinding
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class CardPickerFragment : DialogFragment(R.layout.fragment_card_picker) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<CardPickerViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentCardPickerBinding::bind)

  /**
   * Feature deck adapter.
   */
  private val deckAdapter = CardPickerAdapter()

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Setup deck cards.
    setupDeckList()

    // Setup listeners
    setupDeckListeners()

    // Start feature
    viewModel.initialize()

    // Observe deck state.
    bindState(viewModel.state, ::consumeCardPickerState)
  }


  // endregion

  // region UI manipulation

  private fun consumeCardPickerState(cardPickerStates: List<CardPickerState>) {
    deckAdapter.submitList(cardPickerStates)
  }

  // endregion

  // region Feature setup

  private fun setupDeckList() {
    binding.cardList.adapter = deckAdapter
  }

  private fun setupDeckListeners() {
    binding.clubs.setOnClickListener {
      viewModel.interact(CardPickerInteraction.DeckSuitChanged(CardSuit.CLUBS))
    }
    binding.hearts.setOnClickListener {
      viewModel.interact(CardPickerInteraction.DeckSuitChanged(CardSuit.HEARTS))
    }
    binding.diamonds.setOnClickListener {
      viewModel.interact(CardPickerInteraction.DeckSuitChanged(CardSuit.DIAMONDS))
    }
    binding.spades.setOnClickListener {
      viewModel.interact(CardPickerInteraction.DeckSuitChanged(CardSuit.SPADES))
    }
  }

  // endregion

  // region Builder

  public companion object {
    private const val DISABLED_CARDS = "DISABLED_CARDS"
    private const val COUNT_SELECTION = "COUNT_SELECTION"

    public fun newInstance(countOfSelection: Int, disabledCards: List<Card>): CardPickerFragment {
      return CardPickerFragment().apply {
        arguments = bundleOf(
          COUNT_SELECTION to countOfSelection,
          DISABLED_CARDS to disabledCards,
        )
      }
    }

  }
  // endregion
}