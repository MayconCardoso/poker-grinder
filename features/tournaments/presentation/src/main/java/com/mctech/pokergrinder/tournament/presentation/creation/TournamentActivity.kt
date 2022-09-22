package com.mctech.pokergrinder.tournament.presentation.creation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.tournament.presentation.databinding.ActivityTournamentBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class TournamentActivity : AppCompatActivity() {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<TournamentViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(ActivityTournamentBinding::inflate)

  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Gets tournament
    val tournament = intent.getSerializableExtra(TOURNAMENT_PARAM) as? Tournament
    viewModel.interact(TournamentInteraction.ScreenFirstOpen(tournament))

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
        TournamentInteraction.SaveTournament(
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
      is TournamentCommand.CloseScreen -> finish()
    }
  }

  // endregion

  // region Builder

  internal companion object {
    private const val TOURNAMENT_PARAM = "TOURNAMENT_PARAM"

    fun navigate(
      origin: FragmentActivity,
      tournament: Tournament?,
    ) {
      origin.startActivity(
        Intent(origin, TournamentActivity::class.java).apply {
          putExtra(TOURNAMENT_PARAM, tournament)
        }
      )
    }
  }

  // endregion
}