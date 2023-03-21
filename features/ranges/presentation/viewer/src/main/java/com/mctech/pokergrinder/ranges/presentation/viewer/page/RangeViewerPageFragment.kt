package com.mctech.pokergrinder.ranges.presentation.viewer.page

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.deserialize
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.ranges.domain.entities.EmptyRange
import com.mctech.pokergrinder.ranges.domain.entities.RangeHandInput
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import com.mctech.pokergrinder.ranges.presentation.viewer.R
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RangeViewerPageFragment : Fragment(R.layout.fragment_range_viewer_page) {

  // region Variables

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentRangeViewerPageBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      // Gets range from param
      val range = arguments?.deserialize<RangePosition>(RANGE_POSITION_PARAM) ?: return@avoidFrozenFrames

      // Update screen
      binding.rangeComponent.render(range.hands)
    }
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