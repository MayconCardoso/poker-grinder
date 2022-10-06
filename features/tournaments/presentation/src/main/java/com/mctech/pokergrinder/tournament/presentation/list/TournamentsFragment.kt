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
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.tournament.presentation.R
import com.mctech.pokergrinder.tournament.presentation.TournamentNavigation
import com.mctech.pokergrinder.tournament.presentation.databinding.FragmentTournamentsBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.list.TournamentListCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
public class TournamentsFragment : Fragment(R.layout.fragment_tournaments), TournamentListCallback {

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
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: TournamentNavigation

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
    binding.headerCard.isVisible = false
  }

  private fun rendersSuccess(state: TournamentsState) {
    // Show containers.
    binding.headerCard.isVisible = state.tournaments.isNotEmpty()

    // Render data.
    binding.averageBuyIn.text = state.averageBuyIn
    binding.investment.text = state.investmentPerSession
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.newTournament.setOnClickListener {
      navigateToEditor(tournament = null)
    }
  }

  override fun onTournamentClicked(tournament: Tournament) {
    viewModel.interact(TournamentsInteraction.OnTournamentClicked(tournament))
  }

  // endregion

  // region Commands

  private fun consumeCommands(command: ViewCommand) {
    when (command) {
      is TournamentsCommand.NavigateToEditor -> navigateToEditor(command.tournament)
    }
  }

  private fun navigateToEditor(tournament: Tournament?) {
    navigation.goToTournament(tournament)
  }

  // endregion
}