package com.mctech.pokergrinder.ranges.presentation.viewer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.presentation.navigation.RangeNavigation
import com.mctech.pokergrinder.ranges.presentation.viewer.adapter.RangeContainerAdapter
import com.mctech.pokergrinder.ranges.presentation.viewer.databinding.FragmentRangeViewerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RangeViewerFragment : Fragment(R.layout.fragment_range_viewer) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<RangeViewerViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentRangeViewerBinding::bind)

  /**
   * Tournaments adapter.
   */
  private val rangePagerAdapter by lazy {
    RangeContainerAdapter(
      fragmentManager = childFragmentManager,
      lifecycle = lifecycle,
    )
  }

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: RangeNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      // Gets tournament from param
      val range = arguments?.getSerializable(RANGE_PARAM) as Range

      // Initialize view model
      viewModel.interact(RangeViewerInteraction.OnScreenFirstOpened(range))

      // Observers state changes
      bindState(viewModel.componentState, ::consumeState)

      // Setup List
      setupTournamentPages()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<Range>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.successContainer.isVisible = false
  }

  private fun rendersSuccess(state: Range) {
    // Show containers.
    binding.progress.isVisible = false
    binding.successContainer.isVisible = true

    // Toolbar
    binding.toolbar.title = state.name

    // Pages
    rangePagerAdapter.renderPages(state)
  }

  private fun rendersError() {
    Log.i("RangeViewerFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupTournamentPages() {
    // Set adapter
    binding.containerPager.adapter = rangePagerAdapter
    binding.containerPager.offscreenPageLimit = 3

    // Setup mediator to change title.
    TabLayoutMediator(binding.containerTab, binding.containerPager) { tab, position ->
      tab.text = rangePagerAdapter.getTitleByPosition(position)
    }.attach()
  }

  // endregion

  // region Builder

  companion object {
    const val RANGE_PARAM: String = "RANGE_PARAM"
  }

  // endregion
}