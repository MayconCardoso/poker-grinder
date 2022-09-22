package com.mctech.pokergrinder.bankroll.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveBalanceUseCase
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
internal class BankrollViewModel @Inject constructor(
  private val observeBalanceUseCase: ObserveBalanceUseCase,
  private val observeTransactionsUseCase: ObserveTransactionsUseCase,
) : BaseViewModel() {

  private val _balanceState by lazy {
    MutableStateFlow<ComponentState<String>>(ComponentState.Loading.FromEmpty)
  }
  val balanceState: StateFlow<ComponentState<String>> by lazy { _balanceState }

  private val _transactionState by lazy {
    MutableStateFlow<ComponentState<List<BankrollTransaction>>>(ComponentState.Loading.FromEmpty)
  }
  val transactionState: StateFlow<ComponentState<List<BankrollTransaction>>> by lazy {
    _transactionState
  }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeBalance() }
    viewModelScope.async { observeTransactions() }
  }

  private fun observeBalance() {
    observeBalanceUseCase()
      .onEach { balance ->
        _balanceState.value = ComponentState.Success(DecimalFormat("$#0.00").format(balance))
      }
      .launchIn(viewModelScope)
  }

  private fun observeTransactions() {
    observeTransactionsUseCase()
      .onEach { transactions ->
        _transactionState.value = ComponentState.Success(transactions)
      }
      .launchIn(viewModelScope)
  }

}