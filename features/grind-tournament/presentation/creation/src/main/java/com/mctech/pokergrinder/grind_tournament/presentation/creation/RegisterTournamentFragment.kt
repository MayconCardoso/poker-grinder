package com.mctech.pokergrinder.grind_tournament.presentation.creation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.*
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.presentation.creation.databinding.FragmentRegisterTournamentBinding
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListCallback
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListInteraction
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterTournamentFragment : Fragment(R.layout.fragment_register_tournament),
  TournamentListCallback {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<RegisterTournamentViewModel>()

  /**
   * Tournament View Model
   */
  private val tournamentsViewModel by viewModels<TournamentListViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentRegisterTournamentBinding::bind)

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
    val tournament = arguments?.deserialize<SessionTournament>(TOURNAMENT_PARAM)
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

    // Resolve update profit container
    binding.tournamentUpdateProfit.isVisible = tournament != null
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.save.setOnClickListener {
      val newProfitText = binding.newProfit.text.toString()
      viewModel.interact(
        RegisterTournamentInteraction.SaveTournament(
          title = binding.tournamentTitle.text.toString(),
          buyIn = binding.tournamentBuyIn.text.toString().toDouble(),
          profit = binding.tournamentProfit.text.toString().toDouble(),
          addNewProfit = if (newProfitText.isBlank()) 0.0 else newProfitText.toDouble(),
        )
      )
    }

    binding.tournamentTitle.doOnTextChanged { text, _, _, _ ->
      tournamentsViewModel.interact(TournamentListInteraction.NewFilterQuery(text.toString()))
    }

    listOf(
      binding.tournamentTitle,
      binding.tournamentBuyIn,
      binding.tournamentProfit,
    ).onDataFormFilled { allSet ->
      binding.save.isEnabled = allSet
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

  companion object {
    const val SESSION_PARAM: String = "SESSION_PARAM"
    const val TOURNAMENT_PARAM: String = "TOURNAMENT_PARAM"
  }

  // endregion
}