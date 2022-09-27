package com.mctech.pokergrinder.deck.components.card_picker

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.deck.components.card_picker.databinding.FragmentCardPickerBinding
import com.mctech.pokergrinder.deck.domain.Card
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

  // endregion

  // region Builder

  public companion object {
    private const val COUNT_SELECTION = "COUNT_SELECTION"
    private const val DISABLED_CARDS = "DISABLED_CARDS"

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