package com.mctech.pokergrinder.tournament.presentation.creation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.tournament.presentation.creation.databinding.FragmentTournamentBinding
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class NewTournamentFragment : Fragment(R.layout.fragment_tournament) {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<NewTournamentViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentTournamentBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: TournamentNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Gets tournament
    val tournament = arguments?.getSerializable(TOURNAMENT_PARAM) as? Tournament
    viewModel.interact(NewTournamentInteraction.ScreenFirstOpen(tournament))

    // Setup Listeners
    setupListeners()

    // Observes state.
    bindState(viewModel.componentState, ::rendersState)

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  // endregion

  // region State Manipulation
  private fun rendersState(tournament: Tournament?) {
    val data = tournament ?: return
    binding.tournamentTitle.setText(data.title)
    binding.tournamentBuyIn.setText(data.buyIn.toString())
    binding.tournamentGtd.setText(data.guaranteed.toString())
    binding.tournamentReBuy.setText(data.countReBuy.toString())
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.save.setOnClickListener {
      viewModel.interact(
        NewTournamentInteraction.SaveTournament(
          title = binding.tournamentTitle.text.toString(),
          guaranteed = binding.tournamentGtd.text.toString().toInt(),
          countBuyIn = binding.tournamentReBuy.text.toString().toInt(),
          buyIn = binding.tournamentBuyIn.text.toString().toFloat(),
        )
      )
    }
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is NewTournamentCommand.CloseScreen -> navigation.navigateBack()
    }
  }

  // endregion

  // region Builder

  public companion object {
    public const val TOURNAMENT_PARAM: String = "TOURNAMENT_PARAM"
  }

  // endregion
}