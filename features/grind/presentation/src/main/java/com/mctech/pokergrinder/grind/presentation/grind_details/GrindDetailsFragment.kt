package com.mctech.pokergrinder.grind.presentation.grind_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.R
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentGrindDetailsBinding
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsAdapter
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumer
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumerEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class GrindDetailsFragment : Fragment(R.layout.fragment_grind_details) {

  // region Variables

  /**
   * New Grind View Model
   */
  private val viewModel by viewModels<GrindDetailsViewModel>()

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(FragmentGrindDetailsBinding::bind)

  /**
   * Tournaments adapter event consumer.
   */
  private val tournamentAdapterConsumer by lazy {
    object : GrindDetailsConsumer {
      override fun consume(event: GrindDetailsConsumerEvent) {
        viewModel.interact(GrindDetailsInteraction.OnTournamentEvent(event))
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    GrindDetailsAdapter(eventConsumer = tournamentAdapterConsumer)
  }

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: GrindNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Gets tournament
    val session = arguments?.getSerializable(SESSION_PARAM) as Session
    viewModel.interact(GrindDetailsInteraction.ScreenFirstOpen(session))

    // Setup Listeners
    setupListeners()

    // Setup List
    setupTournamentList()

    // Observes state.
    bindState(viewModel.tournamentsState, ::consumeTournamentsState)

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  // endregion

  // region State Manipulation

  private fun consumeTournamentsState(state: ComponentState<List<SessionTournament>>) {
    when (state) {
      is ComponentState.Error -> rendersTournamentsError()
      is ComponentState.Loading -> rendersTournamentsLoading()
      is ComponentState.Success -> rendersTournamentsSuccess(state.result)
    }
  }

  private fun rendersTournamentsLoading() {
    binding.progress.isVisible = true
    binding.tournaments.isVisible = false
    binding.tournamentsEmpty.isVisible = false
  }

  private fun rendersTournamentsSuccess(state: List<SessionTournament>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.tournaments.isVisible = state.isNotEmpty()
    binding.tournamentsEmpty.isVisible = state.isEmpty()

    // Render list
    tournamentAdapter.submitList(state)
  }

  private fun rendersTournamentsError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.register.setOnClickListener {
      viewModel.interact(GrindDetailsInteraction.RegisterTournamentClicked)
    }
  }

  private fun setupTournamentList() {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.tournaments.adapter = tournamentAdapter
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is GrindDetailsCommand.CloseScreen -> navigation.navigateBack()
      is GrindDetailsCommand.GoToTournamentEditor -> navigateToTournament(command)
      is GrindDetailsCommand.ShowTournamentSelection -> showTournamentSelection()
    }
  }

  private fun navigateToTournament(command: GrindDetailsCommand.GoToTournamentEditor) {
    navigation.goToSessionTournament(command.session, command.sessionTournament)
  }

  private fun showTournamentSelection() {
    TournamentSelectionDialog().show(childFragmentManager, "TournamentSelectionDialog")
  }

  // endregion

  // region Builder

  public companion object {
    public const val SESSION_PARAM: String = "SESSION_PARAM"
  }

  // endregion
}