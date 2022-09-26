package com.mctech.pokergrinder.summary.session

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import com.mctech.pokergrinder.summary.session.databinding.FragmentSummarySessionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class SummarySessionFragment : Fragment(R.layout.fragment_summary_session) {

  // region Variables

  /**
   * Summary View Model
   */
  private val viewModel by viewModels<SummarySessionViewModel>()

  /**
   * Summary Ui Binding
   */
  private val binding by viewBinding(FragmentSummarySessionBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.sessionState, ::consumeSessionState)
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeSessionState(state: ComponentState<SessionSummary>) {
    when (state) {
      is ComponentState.Error -> rendersSessionError()
      is ComponentState.Loading -> rendersSessionLoading()
      is ComponentState.Success -> rendersSessionSuccess(state.result)
    }
  }

  private fun rendersSessionLoading() {
    binding.progressSession.isVisible = true
    binding.groupSession.isInvisible = true
  }

  private fun rendersSessionSuccess(state: SessionSummary) {
    // Show containers.
    binding.progressSession.isVisible = false
    binding.groupSession.isInvisible = false

    // Render data
    binding.countSession.text = state.total.toString()
    binding.positiveSession.text = state.upDays.toString()
    binding.negativeSession.text = state.downDays.toString()
    binding.countTournaments.text = state.tournaments.toString()
  }

  private fun rendersSessionError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

}