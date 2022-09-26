package com.mctech.pokergrinder.grind.presentation.grind_details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.databinding.ActivityGrindDetailsBinding
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsAdapter
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumer
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumerEvent
import com.mctech.pokergrinder.grind.presentation.tournamnet_creation.RegisterTournamentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class GrindDetailsActivity : AppCompatActivity() {

  // region Variables

  /**
   * New Grind View Model
   */
  private val viewModel by viewModels<GrindDetailsViewModel>()

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(ActivityGrindDetailsBinding::inflate)

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
  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Gets tournament
    val session = intent.getSerializableExtra(SESSION_PARAM) as Session
    viewModel.interact(GrindDetailsInteraction.ScreenFirstOpen(session))

    // Setup Listeners
    setupListeners()

    // Setup List
    setupTournamentList()

    // Observes state.
    bindState(viewModel.detailsState, ::consumeDetailState)
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

  private fun consumeDetailState(state: ComponentState<Session>) {
    when (state) {
      is ComponentState.Error -> rendersSessionError()
      is ComponentState.Loading -> rendersSessionLoading()
      is ComponentState.Success -> rendersSessionSuccess(state.result)
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

  private fun rendersSessionLoading() {
    binding.balance.isVisible = false
    binding.progressBalance.isVisible = true

    binding.buyIn.isVisible = false
    binding.progressBuyIn.isVisible = true
  }

  private fun rendersSessionSuccess(session: Session) {
    binding.toolbar.title = session.title

    binding.balance.text = session.formattedBalance()
    binding.balance.isVisible = true
    binding.progressBalance.isVisible = false

    binding.buyIn.text = session.tournamentsPlayed.toString()
    binding.buyIn.isVisible = true
    binding.progressBuyIn.isVisible = false
  }


  private fun rendersSessionError() {
    TODO("Not yet implemented")
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
      is GrindDetailsCommand.CloseScreen -> finish()
      is GrindDetailsCommand.GoToTournamentEditor -> navigateToTournament(command)
    }
  }

  private fun navigateToTournament(command: GrindDetailsCommand.GoToTournamentEditor) {
    RegisterTournamentActivity.navigate(
      origin = this,
      session = command.session,
      sessionTournament = command.sessionTournament,
    )
  }

  // endregion

  // region Builder

  internal companion object {
    private const val SESSION_PARAM = "SESSION_PARAM"

    fun navigate(origin: FragmentActivity, session: Session) {
      origin.startActivity(
        Intent(origin, GrindDetailsActivity::class.java).apply {
          putExtra(SESSION_PARAM, session)
        }
      )
    }
  }

  // endregion
}