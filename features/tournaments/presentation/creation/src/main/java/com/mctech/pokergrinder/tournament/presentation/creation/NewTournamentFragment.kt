package com.mctech.pokergrinder.tournament.presentation.creation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.*
import com.mctech.pokergrinder.tournament.presentation.creation.databinding.FragmentTournamentBinding
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
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
    val tournament = arguments?.deserialize<Tournament>(TOURNAMENT_PARAM) ?: return
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
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    // Click on save button.
    binding.save.setOnClickListener {
      viewModel.interact(
        NewTournamentInteraction.SaveTournament(
          title = binding.tournamentTitle.text.toString(),
          buyIn = binding.tournamentBuyIn.text.toString().toFloat(),
        )
      )
    }

    // Observe fields content to enable/disable save button.
    listOf(binding.tournamentTitle, binding.tournamentBuyIn).onDataFormFilled { allSet ->
      binding.save.isEnabled = allSet
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