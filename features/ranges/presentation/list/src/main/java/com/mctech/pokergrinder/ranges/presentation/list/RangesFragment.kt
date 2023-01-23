package com.mctech.pokergrinder.ranges.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangeAdapterConsumer
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangeAdapterConsumerEvent
import com.mctech.pokergrinder.ranges.presentation.list.adapter.RangesAdapter
import com.mctech.pokergrinder.ranges.presentation.list.databinding.FragmentRangesBinding
import com.mctech.pokergrinder.ranges.presentation.navigation.RangeNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RangesFragment : Fragment(R.layout.fragment_ranges) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<RangesViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentRangesBinding::bind)

  /**
   * Grinds adapter event consumer.
   */
  private val rangeAdapterConsumer by lazy {
    object : RangeAdapterConsumer {
      override fun consume(event: RangeAdapterConsumerEvent) {
        viewModel.interact(RangesInteraction.OnRangeEvent(event))
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val grindsAdapter by lazy {
    RangesAdapter(eventConsumer = rangeAdapterConsumer)
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
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.componentState, ::consumeState)

      // Observers commands
      bindCommand(viewModel, ::consumeCommands)

      // Setup List
      setupTournamentList()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<List<Range>>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.ranges.isVisible = false
    binding.grindsEmpty.isVisible = false
  }

  private fun rendersSuccess(state: List<Range>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.ranges.isVisible = state.isNotEmpty()
    binding.grindsEmpty.isVisible = state.isEmpty()

    // Render list
    grindsAdapter.submitList(state)
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupTournamentList() {
    binding.ranges.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.ranges.adapter = grindsAdapter
  }

  // endregion

  // region Commands

  private fun consumeCommands(command: ViewCommand) {
    when (command) {
      is RangesCommand.NavigateToViewer -> navigateToSession(command.range)
    }
  }

  private fun navigateToSession(range: Range) {
    navigation.goToRangeDetails(range)
  }

  // endregion
}