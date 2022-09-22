package com.mctech.pokergrinder.tournament.presentation.list

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
import com.mctech.pokergrinder.tournament.presentation.R
import com.mctech.pokergrinder.tournament.presentation.creation.TournamentActivity
import com.mctech.pokergrinder.tournament.presentation.databinding.FragmentTournamentsBinding
import com.mctech.pokergrinder.tournament.presentation.list.adapter.TournamentsAdapter
import com.mctech.pokergrinder.tournament.presentation.list.adapter.TournamentsAdapterConsumer
import com.mctech.pokergrinder.tournament.presentation.list.adapter.TournamentsAdapterConsumerEvent
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class TournamentsFragment : Fragment(R.layout.fragment_tournaments) {

  // region Variables

  /**
   * Tournaments View Model
   */
  private val viewModel by viewModels<TournamentsViewModel>()

  /**
   * Tournaments Ui Binding
   */
  private val binding by viewBinding(FragmentTournamentsBinding::bind)

  /**
   * Tournaments adapter event consumer.
   */
  private val tournamentAdapterConsumer by lazy {
    object : TournamentsAdapterConsumer {
      override fun consume(event: TournamentsAdapterConsumerEvent) {
        viewModel.interact(TournamentsInteraction.OnTournamentEvent(event))
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    TournamentsAdapter(eventConsumer = tournamentAdapterConsumer)
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

  private fun consumeState(state: ComponentState<TournamentsState>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.headerCard.isVisible = false
    binding.tournaments.isVisible = false
    binding.tournamentsEmpty.isVisible = false
  }

  private fun rendersSuccess(state: TournamentsState) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = state.tournaments.isNotEmpty()
    binding.headerCard.isVisible = state.tournaments.isNotEmpty()
    binding.tournamentsEmpty.isVisible = state.tournaments.isEmpty()

    // Render data.
    binding.averageBuyIn.text = state.averageBuyIn
    binding.investment.text = state.investmentPerSession

    // Render list
    tournamentAdapter.submitList(state.tournaments)
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

  private fun setupListeners() {
    binding.newTournament.setOnClickListener {
      navigateToEditor(tournament = null)
    }
  }

  // endregion

  // region Commands

  private fun consumeCommands(command: ViewCommand) {
    when (command) {
      is TournamentsCommand.NavigateToEditor -> navigateToEditor(command.tournament)
    }
  }

  private fun navigateToEditor(tournament: Tournament?) {
    TournamentActivity.navigate(requireActivity(), tournament)
  }

  // endregion
}