package com.mctech.pokergrinder.grind.presentation.list

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
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapter
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumer
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent
import com.mctech.pokergrinder.grind.presentation.list.databinding.FragmentGrindsBinding
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: GrindNavigation

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
      viewModel.interact(GrindsInteraction.NewSessionClicked)
    }
  }

  // endregion

  // region Commands

  private fun consumeCommands(command: ViewCommand) {
    when (command) {
      is GrindsCommand.NavigateToNewSession -> navigateToNewSession()
      is GrindsCommand.NavigateToSessionDetails -> navigateToSession(command.session)
    }
  }

  private fun navigateToNewSession() {
    navigation.goToNewSession()
  }

  private fun navigateToSession(session: Session) {
    navigation.goToSessionDetails(session)
  }

  // endregion
}