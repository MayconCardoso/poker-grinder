package com.mctech.pokergrinder.summary.presentation.tournaments.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.navigation.SummaryNavigation
import com.mctech.pokergrinder.summary.presentation.tournament.R
import com.mctech.pokergrinder.summary.presentation.tournament.databinding.FragmentSummaryTournamentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SummaryTournamentDetailsFragment : Fragment(R.layout.fragment_summary_tournament_detail) {

  // region Variables

  /**
   * Tournaments View Model
   */
  private val viewModel by viewModels<SummaryTournamentDetailsViewModel>()

  /**
   * Tournaments Ui Binding
   */
  private val binding by viewBinding(FragmentSummaryTournamentDetailBinding::bind)

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    SummaryTournamentDetailsAdapter(onTournamentClick = ::onTournamentClicked)
  }

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: SummaryNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Gets tournament from param
      val tournament = arguments?.getSerializable(TOURNAMENT_PARAM) as TournamentSummary

      // Setup List
      setupTournamentList()

      // Start component
      viewModel.interact(SummaryTournamentDetailsInteraction.OnScreenFirstOpened(tournament))

      // Observers state changes
      bindState(viewModel.tournamentState, ::consumeTournamentState)

      // Observers chart state changes
      bindState(viewModel.performanceState, ::consumeChartState)
    }
  }
  // endregion

  // region State Manipulation

  private fun consumeTournamentState(state: ComponentState<SummaryTournamentDetailsState>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun consumeChartState(state: List<MoneyVariationEntry>) {
    binding.chart.render(state)
  }

  private fun rendersLoading() {
    binding.progress.isVisible = true
    binding.tournaments.isVisible = false
  }

  private fun rendersSuccess(state: SummaryTournamentDetailsState) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = state.tournaments.isNotEmpty()

    // Render list
    tournamentAdapter.submitList(state.tournaments)

    // Played times
    binding.summary.render(state.groupedSummary)
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupTournamentList() {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 6.dp()))
    binding.tournaments.adapter = tournamentAdapter
  }

  private fun onTournamentClicked(tournamentSummary: TournamentSummary) {
    Log.i("TournamentsFragment", "$tournamentSummary")
    // TODO near future.
  }

  // endregion


  // region Builder

  companion object {
    const val TOURNAMENT_PARAM: String = "TOURNAMENT_PARAM"
  }

  // endregion
}