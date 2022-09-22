package com.mctech.pokergrinder.bankroll.presentation.creation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.bankroll.presentation.databinding.ActivityDepositBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class DepositActivity : AppCompatActivity() {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<DepositViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(ActivityDepositBinding::inflate)

  // endregion

  // region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    // Setup Listeners
    setupListeners()

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.save.setOnClickListener {
      viewModel.interact(
        DepositInteraction.SaveDeposit(
          title = binding.depositTitle.text.toString(),
          amount = binding.amount.text.toString().toDouble(),
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
    fun navigate(origin: FragmentActivity) {
      origin.startActivity(Intent(origin, DepositActivity::class.java))
    }
  }

  // endregion
}