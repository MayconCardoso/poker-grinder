package com.mctech.pokergrinder.bankroll.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.presentation.R
import com.mctech.pokergrinder.bankroll.presentation.creation.DepositActivity
import com.mctech.pokergrinder.bankroll.presentation.databinding.FragmentBankrollBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class BankrollFragment : Fragment(R.layout.fragment_bankroll) {

  // region Variables

  /**
   * Bankroll View Model
   */
  private val viewModel by viewModels<BankrollViewModel>()

  /**
   * Bankroll Ui Binding
   */
  private val binding by viewBinding(FragmentBankrollBinding::bind)

  /**
   * Bankroll adapter.
   */
  private val bankrollAdapter by lazy { BankrollAdapter() }

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
      bindState(viewModel.balanceState, ::consumeBalanceState)
      bindState(viewModel.transactionState, ::consumeTransactionState)

      // Setup List
      setupTransactionList()

      // Setup Listeners
      setupListeners()
    }
  }

  // endregion

  // region State Manipulation

  private fun consumeTransactionState(state: ComponentState<List<BankrollTransaction>>) {
    when (state) {
      is ComponentState.Error -> rendersTransactionError()
      is ComponentState.Loading -> rendersTransactionLoading()
      is ComponentState.Success -> rendersTransactionSuccess(state.result)
    }
  }

  private fun consumeBalanceState(state: ComponentState<String>) {
    when (state) {
      is ComponentState.Error -> rendersBalanceError()
      is ComponentState.Loading -> rendersBalanceLoading()
      is ComponentState.Success -> rendersBalanceSuccess(state.result)
    }
  }

  private fun rendersTransactionLoading() {
    binding.progress.isVisible = true
    binding.transactions.isVisible = false
    binding.transactionsEmpty.isVisible = false
  }

  private fun rendersTransactionSuccess(state: List<BankrollTransaction>) {
    // Show containers.
    binding.progress.isVisible = false
    binding.transactions.isVisible = state.isNotEmpty()
    binding.transactionsEmpty.isVisible = state.isEmpty()

    // Render list
    bankrollAdapter.submitList(state)
  }

  private fun rendersTransactionError() {
    Log.i("TournamentsFragment", "Error while loading screen.")
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

  // region Component Setup

  private fun setupTransactionList() {
    binding.transactions.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.transactions.adapter = bankrollAdapter
  }

  private fun setupListeners() {
    binding.newTransaction.setOnClickListener {
      navigateToEditor()
    }
  }

  // endregion

  // region Commands

  private fun navigateToEditor() {
    DepositActivity.navigate(requireActivity())
  }

  // endregion
}