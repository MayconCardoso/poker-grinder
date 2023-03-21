package com.mctech.pokergrinder.grind_summary.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.deserialize
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind_summary.presentation.databinding.FragmentGrindDetailsSummaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GrindSummaryFragment : Fragment(R.layout.fragment_grind_details_summary) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<GrindSummaryViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentGrindDetailsSummaryBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Gets tournament
    val session = arguments?.deserialize<Session>(SESSION_PARAM) ?: return
    viewModel.interact(GrindSummaryInteraction.ScreenFirstOpen(session))

    // Observes state.
    bindState(viewModel.flipState, ::consumeFlipState)
    bindState(viewModel.chartState, ::consumeChartState)
    bindState(viewModel.detailsState, ::consumeDetailState)
  }

  // endregion

  // region State Manipulation

  private fun consumeDetailState(state: ComponentState<Session>) {
    when (state) {
      is ComponentState.Error -> rendersSessionError()
      is ComponentState.Loading -> rendersSessionLoading()
      is ComponentState.Success -> rendersSessionSuccess(state.result)
    }
  }

  private fun consumeFlipState(data: List<SessionTournamentFlip>) {
    binding.flipWon.text = data.count { it.won }.toString()
    binding.flipsLost.text = data.count { !it.won }.toString()
  }

  private fun consumeChartState(data: List<MoneyVariationEntry>) {
    binding.noData.isVisible = data.isEmpty()
    binding.chart.render(data)
  }

  private fun rendersSessionLoading() {
    binding.balance.isVisible = false
    binding.progressBalance.isVisible = true

    binding.buyIn.isVisible = false
    binding.progressBuyIn.isVisible = true

    binding.progressTournament.isVisible = true

    binding.cash.isVisible = false
    binding.progressCash.isVisible = true
  }

  private fun rendersSessionSuccess(session: Session) {
    binding.balance.text = session.formattedBalance()
    binding.balance.isVisible = true
    binding.progressBalance.isVisible = false

    binding.tournament.text = session.tournamentsPlayed.toString()
    binding.tournament.isVisible = true
    binding.progressTournament.isVisible = false

    binding.buyIn.text = session.formattedBuyIn()
    binding.buyIn.isVisible = true
    binding.progressBuyIn.isVisible = false

    binding.cash.text = session.formattedCash()
    binding.cash.isVisible = true
    binding.progressCash.isVisible = false
  }

  private fun rendersSessionError() {
    // TODO("Not yet implemented")
  }

  // endregion

  // region Builder

  companion object {
    const val SESSION_PARAM: String = "SESSION_PARAM"
  }

  // endregion
}