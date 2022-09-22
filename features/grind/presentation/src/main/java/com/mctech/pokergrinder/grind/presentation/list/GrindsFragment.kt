package com.mctech.pokergrinder.grind.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.R
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentGrindsBinding
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapter
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumer
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class GrindsFragment : Fragment(R.layout.fragment_grinds) {

  // region Variables

  /**
   * Grinds View Model
   */
  private val viewModel by viewModels<GrindsViewModel>()

  /**
   * Grinds Ui Binding
   */
  private val binding by viewBinding(FragmentGrindsBinding::bind)

  /**
   * Grinds adapter event consumer.
   */
  private val grindsAdapterConsumer by lazy {
    object : GrindAdapterConsumer {
      override fun consume(event: GrindAdapterConsumerEvent) {
        viewModel.interact(GrindsInteraction.OnGrindEvent(event))
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val grindsAdapter by lazy {
    GrindAdapter(eventConsumer = grindsAdapterConsumer)
  }

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.componentState, ::consumeState)

      // Observers commands
      bindCommand(viewModel, ::consumeCommands)

      // Setup List
      setupTournamentList()

      // Setup Listeners
      setupListeners()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<List<Session>>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.grinds.isVisible = false
    binding.grindsEmpty.isVisible = false
  }

  private fun rendersSuccess(state: List<Session>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.grinds.isVisible = state.isNotEmpty()
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
    binding.grinds.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.grinds.adapter = grindsAdapter
  }

  private fun setupListeners() {
    binding.newGrind.setOnClickListener {
      navigateToEditor()
    }
  }

  // endregion

  // region Commands

  private fun consumeCommands(command: ViewCommand) {
    Log.i("TournamentsFragment", "$command Error while loading screen.")
//    when (command) {
//      is TournamentsCommand.NavigateToEditor -> navigateToEditor(command.tournament)
//    }
  }

  private fun navigateToEditor() {
//    TournamentActivity.navigate(requireActivity(), tournament)
  }

  // endregion
}