package com.mctech.pokergrinder.ranges.presentation.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.deserialize
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.ranges.domain.entities.EmptyRange
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerDialogBinding
import com.mctech.pokergrinder.ranges.presentation.viewer.page.RangeViewerPageFragment

class RangeViewerDialog : BottomSheetDialogFragment() {

  // region Variable

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentRangeViewerDialogBinding::bind)

  // endregion

  // region Lifecycle

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return FragmentRangeViewerDialogBinding.inflate(inflater, container, false).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      // Gets range from param
      val range = arguments?.deserialize<RangePosition>(RANGE_POSITION_PARAM) ?: return@avoidFrozenFrames

      // Update screen
      binding.rangeComponent.render(resolveEmptyRangeHands(range))
    }
  }

  private fun resolveEmptyRangeHands(position: RangePosition): List<RangeHandInput> {
    // Filled inputs
    val filledInputs = position.hands.associateBy { it.hand.formattedName() }

    // Create the entire range
    val inputs = EmptyRange.rangeHands.map { emptyHand ->
      if (filledInputs.containsKey(emptyHand.formattedName())) {
        filledInputs[emptyHand.formattedName()]
      } else {
        RangeHandInput(
          hand = emptyHand,
          filledColor = "#FFFFFF"
        )
      }
    }

    return inputs.filterNotNull()
  }

  // endregion

  // region Builder

  companion object {
    const val RANGE_POSITION_PARAM: String = "RANGE_POSITION_PARAM"

    operator fun invoke(position: RangePosition) = RangeViewerPageFragment().apply {
      arguments = bundleOf(
        RANGE_POSITION_PARAM to position
      )
    }
  }

  // endregion

}