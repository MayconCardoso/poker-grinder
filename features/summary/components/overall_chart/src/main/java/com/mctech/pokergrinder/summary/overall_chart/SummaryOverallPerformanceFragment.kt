package com.mctech.pokergrinder.summary.overall_chart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.overall_chart.databinding.FragmentSummaryOverallPerformanceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class SummaryOverallPerformanceFragment :
  Fragment(R.layout.fragment_summary_overall_performance) {

  // region Variables

  /**
   * Summary View Model
   */
  private val viewModel by viewModels<SummaryOverallPerformanceViewModel>()

  /**
   * Summary Ui Binding
   */
  private val binding by viewBinding(FragmentSummaryOverallPerformanceBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.performanceState, ::consumeState)
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeState(state: ComponentState<List<MoneyVariationEntry>>) {
    when (state) {
      is ComponentState.Error -> rendersError()
      is ComponentState.Loading -> rendersLoading()
      is ComponentState.Success -> rendersSuccess(state.result)
    }
  }

  private fun rendersLoading() {
    binding.progressPerformance.isVisible = true
    binding.chartContainer.isInvisible = true
  }

  private fun rendersSuccess(state: List<MoneyVariationEntry>) {
    binding.progressPerformance.isVisible = false
    binding.chart.render(state)
    binding.chartContainer.isInvisible = false
  }

  private fun rendersError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

}