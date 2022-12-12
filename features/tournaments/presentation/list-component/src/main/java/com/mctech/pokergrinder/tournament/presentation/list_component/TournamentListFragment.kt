package com.mctech.pokergrinder.tournament.presentation.list_component

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.tournament.presentation.list_component.databinding.FragmentTournamentListBinding
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class TournamentListFragment : Fragment(R.layout.fragment_tournament_list) {

  // region Variables

  /**
   * Tournaments View Model
   */
  private val viewModel by viewModels<TournamentListViewModel>(
    ownerProducer = { requireParentFragment() }
  )

  /**
   * Tournaments Ui Binding
   */
  private val binding by viewBinding(FragmentTournamentListBinding::bind)

  /**
   * Gets the client callback.
   */
  private val clickCallback by lazy {
    (parentFragment as? TournamentListCallback) ?: requireActivity() as TournamentListCallback
  }

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    TournamentListAdapter { tournament ->
      clickCallback.onTournamentClicked(tournament)
    }
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

      // Setup List
      setupTournamentList()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<List<Tournament>>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.tournaments.isVisible = false
    binding.tournamentsEmpty.isVisible = false
  }

  private fun rendersSuccess(state: List<Tournament>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = state.isNotEmpty()
    binding.tournamentsEmpty.isVisible = state.isEmpty()

    // Render list
    tournamentAdapter.submitList(state)
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupTournamentList() {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.tournaments.adapter = tournamentAdapter
  }

  // endregion

}