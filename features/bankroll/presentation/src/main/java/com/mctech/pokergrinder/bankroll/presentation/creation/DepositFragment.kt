package com.mctech.pokergrinder.bankroll.presentation.creation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.bankroll.presentation.R
import com.mctech.pokergrinder.bankroll.presentation.databinding.FragmentDepositBinding
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class DepositFragment : Fragment(R.layout.fragment_deposit) {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<DepositViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentDepositBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: BankrollNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
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
      is TournamentCommand.CloseScreen -> navigation.navigateBack()
    }
  }

  // endregion
}