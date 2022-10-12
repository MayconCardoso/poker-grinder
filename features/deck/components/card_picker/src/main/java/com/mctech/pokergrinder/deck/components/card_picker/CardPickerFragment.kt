package com.mctech.pokergrinder.deck.components.card_picker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.deck.components.card_picker.adapter.CardPickerAdapter
import com.mctech.pokergrinder.deck.components.card_picker.adapter.CardPickerConsumer
import com.mctech.pokergrinder.deck.components.card_picker.adapter.CardPickerConsumerEvent
import com.mctech.pokergrinder.deck.components.card_picker.databinding.FragmentCardPickerBinding
import com.mctech.pokergrinder.deck.domain.CardSuit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class CardPickerFragment : Fragment(R.layout.fragment_card_picker) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<CardPickerViewModel>(
    ownerProducer = { requireParentFragment() }
  )

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentCardPickerBinding::bind)

  /**
   * Feature adapter event consumer.
   */
  private val cardAdapterConsumer by lazy {
    object : CardPickerConsumer {
      override fun consume(event: CardPickerConsumerEvent) {
        viewModel.interact(CardPickerInteraction.CardEvent(event))
      }
    }
  }

  /**
   * Feature deck adapter.
   */
  private val deckAdapter by lazy {
    CardPickerAdapter(consumer = cardAdapterConsumer)
  }

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
    binding.cardList.itemAnimator = null
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

}