package com.mctech.pokergrinder.grind_gameplay.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.deserialize
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind_gameplay.presentation.list.databinding.FragmentGrindFlipsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GrindDetailsGameplayFragment : Fragment(R.layout.fragment_grind_flips) {

  // region Variables

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<GrindDetailsGameplayViewModel>()

  /**
   * Feature Ui Binding
   */
  private val binding by viewBinding(FragmentGrindFlipsBinding::bind)

  /**
   * Feature flips adapter.
   */
  private val adapter by lazy {
    GrindDetailsGameplayAdapter()
  }

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: GrindNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Gets tournament
    val session = arguments?.deserialize<Session>(SESSION_PARAM) ?: return
    viewModel.interact(GrindDetailsGameplayInteraction.ScreenFirstOpen(session))

    // Setup Listeners
    setupListeners()

    // Setup List
    setupTournamentFlipsList()

    // Observes state.
    bindState(viewModel.state, ::consumeGameplayState)
  }

  // endregion

  // region State Manipulation

  private fun consumeGameplayState(state: ComponentState<List<SessionTournamentFlip>>) {
    when (state) {
      is ComponentState.Error -> rendersTournamentsFlipError()
      is ComponentState.Loading -> rendersTournamentsFlipLoading()
      is ComponentState.Success -> rendersTournamentsFlipsSuccess(state.result)
    }
  }

  private fun rendersTournamentsFlipLoading() {
    binding.progress.isVisible = true
    binding.tournaments.isVisible = false
    binding.tournamentsEmpty.isVisible = false
  }

  private fun rendersTournamentsFlipsSuccess(state: List<SessionTournamentFlip>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = state.isNotEmpty()
    binding.tournamentsEmpty.isVisible = state.isEmpty()

    // Render list
    adapter.submitList(state)
  }

  private fun rendersTournamentsFlipError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.register.setOnClickListener {
      navigation.goToSessionTournamentGameplay(viewModel.session)
    }
  }

  private fun setupTournamentFlipsList() {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.tournaments.adapter = adapter
  }

  // endregion
  // region Builder

  companion object {
    const val SESSION_PARAM: String = "SESSION_PARAM"
  }

  // endregion
}