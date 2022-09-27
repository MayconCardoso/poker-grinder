package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.R
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentRegisterTournamentBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.list.TournamentListCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class RegisterTournamentFragment : Fragment(R.layout.fragment_register_tournament), TournamentListCallback {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<RegisterTournamentViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentRegisterTournamentBinding::bind)

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
    val tournament = arguments?.getSerializable(TOURNAMENT_PARAM) as? SessionTournament
    viewModel.interact(RegisterTournamentInteraction.ScreenFirstOpen(session, tournament))

    // Setup Listeners
    setupListeners()

    // Observes state.
    bindState(viewModel.componentState, ::rendersState)

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  // endregion

  // region State Manipulation

  private fun rendersState(tournament: SessionTournament?) {
    binding.tournamentTitle.setText(tournament?.title)
    binding.tournamentBuyIn.setText(tournament?.buyIn?.toString())

    // Resolve profit
    binding.tournamentProfit.isVisible = tournament != null
    binding.tournamentSelection.isVisible = tournament == null
    binding.tournamentProfit.setText(tournament?.profit?.toString() ?: "0")
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.save.setOnClickListener {
      viewModel.interact(
        RegisterTournamentInteraction.SaveTournament(
          title = binding.tournamentTitle.text.toString(),
          buyIn = binding.tournamentBuyIn.text.toString().toDouble(),
          profit = binding.tournamentProfit.text.toString().toDouble(),
        )
      )
    }
  }

  override fun onTournamentClicked(tournament: Tournament) {
    binding.tournamentTitle.setText(tournament.title)
    binding.tournamentBuyIn.setText(tournament.buyIn.toString())
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is RegisterTournamentCommand.CloseScreen -> navigation.navigateBack()
      is RegisterTournamentCommand.InsufficientBalanceError -> showError()
    }
  }

  private fun showError() {
    Toast.makeText(
      requireActivity(),
      com.mctech.pokergrinder.localization.R.string.insufficient_balance,
      Toast.LENGTH_SHORT,
    ).show()
  }

  // endregion

  // region Builder

  public companion object {
    public const val SESSION_PARAM: String = "SESSION_PARAM"
    public const val TOURNAMENT_PARAM: String = "TOURNAMENT_PARAM"
  }

  // endregion
}