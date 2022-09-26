package com.mctech.pokergrinder.bankroll.balance

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.bankroll.balance.databinding.FragmentBankrollBalanceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class BankrollBalanceFragment : Fragment(R.layout.fragment_bankroll_balance) {

  // region Variables

  /**
   * Bankroll View Model
   */
  private val viewModel by viewModels<BankrollBalanceViewModel>()

  /**
   * Bankroll Ui Binding
   */
  private val binding by viewBinding(FragmentBankrollBalanceBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.balanceState, ::consumeBalanceState)
    }
  }

  // endregion

  // region State Manipulation


  private fun consumeBalanceState(state: ComponentState<String>) {
    when (state) {
      is ComponentState.Error -> rendersBalanceError()
      is ComponentState.Loading -> rendersBalanceLoading()
      is ComponentState.Success -> rendersBalanceSuccess(state.result)
    }
  }

  private fun rendersBalanceLoading() {
    binding.balance.isVisible = false
    binding.progressBalance.isVisible = true
  }

  private fun rendersBalanceSuccess(balance: String) {
    binding.balance.text = balance
    binding.balance.isVisible = true
    binding.progressBalance.isVisible = false
  }

  private fun rendersBalanceError() {
    TODO("Not yet implemented")
  }

  // endregion

}