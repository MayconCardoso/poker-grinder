package com.mctech.pokergrinder.tournament.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.tournament.presentation.R
import com.mctech.pokergrinder.tournament.presentation.databinding.FragmentTournamentsBinding
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

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.componentState, ::consumeState)
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

  private fun rendersSuccess(result: TournamentsState) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = result.tournaments.isNotEmpty()
    binding.headerCard.isVisible = result.tournaments.isNotEmpty()
    binding.tournamentsEmpty.isVisible = result.tournaments.isEmpty()

    // Render data.
    binding.averageBuyIn.text = result.averageBuyIn
    binding.investment.text = result.investmentPerSession
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion
}