package com.mctech.pokergrinder.ranges.presentation.viewer.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import com.mctech.pokergrinder.ranges.presentation.navigation.RangeNavigation
import com.mctech.pokergrinder.ranges.presentation.viewer.R
import com.mctech.pokergrinder.ranges.presentation.viewer.RangeViewerFragment
import com.mctech.pokergrinder.ranges.presentation.viewer.adapter.RangeContainerAdapter
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerBinding
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerPageBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
      val range = arguments?.getSerializable(RANGE_POSITION_PARAM) as RangePosition

      // Update screen
      binding.text.text = range.position.name
    }
  }

  // endregion

  // region State Manipulation

  // endregion

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