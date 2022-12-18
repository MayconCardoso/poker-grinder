package com.mctech.pokergrinder.bankroll.presentation.withdraw

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.onDataFormFilled
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.bankroll.presentation.withdraw.databinding.FragmentWithdrawBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class WithdrawFragment : Fragment(R.layout.fragment_withdraw) {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<WithdrawViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentWithdrawBinding::bind)

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
        WithdrawInteraction.SaveDeposit(
          title = binding.depositTitle.text.toString(),
          amount = binding.amount.text.toString().toDouble(),
        )
      )
    }

    listOf(binding.depositTitle, binding.amount).onDataFormFilled { allSet ->
      binding.save.isEnabled = allSet
    }
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is WithdrawCommand.CloseScreen -> navigation.navigateBack()
      is WithdrawCommand.InsufficientBalanceError -> showError()
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
}