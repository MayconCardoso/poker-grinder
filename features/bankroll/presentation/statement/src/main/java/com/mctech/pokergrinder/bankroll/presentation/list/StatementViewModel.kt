package com.mctech.pokergrinder.bankroll.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class StatementViewModel @Inject constructor(
  private val observeTransactionsUseCase: ObserveTransactionsUseCase,
) : BaseViewModel() {

  private val _transactionState by lazy {
    MutableStateFlow<ComponentState<List<BankrollTransaction>>>(ComponentState.Loading.FromEmpty)
  }
  val transactionState: StateFlow<ComponentState<List<BankrollTransaction>>> by lazy {
    _transactionState
  }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeTransactions() }
  }

  private fun observeTransactions() {
    observeTransactionsUseCase()
      .onEach { transactions ->
        _transactionState.value = ComponentState.Success(transactions)
      }
      .launchIn(viewModelScope)
  }

}