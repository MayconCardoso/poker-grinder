package com.mctech.pokergrinder.grind.presentation.tournamnet_creation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.databinding.ActivityRegisterTournamentBinding
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.list.TournamentListCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class RegisterTournamentActivity : AppCompatActivity(), TournamentListCallback {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<RegisterTournamentViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(ActivityRegisterTournamentBinding::inflate)

  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Gets tournament
    val session = intent.getSerializableExtra(SESSION_PARAM) as Session
    val tournament = intent.getSerializableExtra(TOURNAMENT_PARAM) as? SessionTournament
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
      is RegisterTournamentCommand.CloseScreen -> finish()
      is RegisterTournamentCommand.InsufficientBalanceError -> showError()
    }
  }

  private fun showError() {
    Toast.makeText(
      this,
      com.mctech.pokergrinder.localization.R.string.insufficient_balance,
      Toast.LENGTH_SHORT,
    ).show()
  }

  // endregion

  // region Builder

  internal companion object {
    private const val SESSION_PARAM = "SESSION_PARAM"
    private const val TOURNAMENT_PARAM = "TOURNAMENT_PARAM"

    fun navigate(
      origin: FragmentActivity,
      session: Session?,
      sessionTournament: SessionTournament?,
    ) {
      origin.startActivity(
        Intent(origin, RegisterTournamentActivity::class.java).apply {
          putExtra(SESSION_PARAM, session)
          putExtra(TOURNAMENT_PARAM, sessionTournament)
        }
      )
    }
  }

  // endregion
}