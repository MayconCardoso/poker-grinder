package com.mctech.pokergrinder.bankroll.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.presentation.list.databinding.FragmentBankrollBinding
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

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

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: BankrollNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      // Initialize view model
      viewModel.initialize()

      // Observers state changes
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

  // endregion

  // region Component Setup

  private fun setupTransactionList() {
    binding.transactions.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.transactions.adapter = bankrollAdapter
  }

  private fun setupListeners() {
    binding.deposit.setOnClickListener {
      navigation.goToBankrollDeposit()

    }
    binding.withdraw.setOnClickListener {
      navigation.goToBankrollWithdraw()
    }
  }

  // endregion
}