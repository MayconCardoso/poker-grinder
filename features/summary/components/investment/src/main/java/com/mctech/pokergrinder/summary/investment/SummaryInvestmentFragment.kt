package com.mctech.pokergrinder.summary.investment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.investment.databinding.FragmentSummaryInvestmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class SummaryInvestmentFragment : Fragment(R.layout.fragment_summary_investment) {

  // region Variables

  /**
   * Summary View Model
   */
  private val viewModel by viewModels<SummaryInvestmentViewModel>()

  /**
   * Summary Ui Binding
   */
  private val binding by viewBinding(FragmentSummaryInvestmentBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.investmentState, ::consumeInvestmentState)
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeInvestmentState(state: ComponentState<InvestmentSummary>) {
    when (state) {
      is ComponentState.Error -> rendersInvestmentError()
      is ComponentState.Loading -> rendersInvestmentLoading()
      is ComponentState.Success -> rendersInvestmentSuccess(state.result)
    }
  }

  private fun rendersInvestmentLoading() {
    // Show containers.
    binding.progressInvestment.isVisible = true
    binding.groupInvestment.isInvisible = true
  }

  private fun rendersInvestmentSuccess(state: InvestmentSummary) {
    binding.progressInvestment.isVisible = false
    binding.groupInvestment.isInvisible = false

    // Render profit
    binding.profit.text = state.formattedProfit()
    binding.profit.setTextColor(
      ContextCompat.getColor(
        binding.root.context,
        if (state.profit >= 0) com.mctech.pokergrinder.design.R.color.deposit
        else com.mctech.pokergrinder.design.R.color.withdraw
      )
    )

    // Render roi
    binding.roi.text = state.formattedRoi()
    binding.roi.setTextColor(
      ContextCompat.getColor(
        binding.root.context,
        if (state.computeRoi() >= 0) com.mctech.pokergrinder.design.R.color.deposit
        else com.mctech.pokergrinder.design.R.color.withdraw
      )
    )

    binding.cash.text = state.formattedCash()
    binding.investment.text = state.formattedBuyIn()
  }

  private fun rendersInvestmentError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

}