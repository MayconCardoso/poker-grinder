package com.mctech.pokergrinder.bankroll.presentation.balance_component

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
internal class BankrollBalanceViewModel @Inject constructor(
  private val observeBalanceUseCase: ObserveBalanceUseCase,
) : BaseViewModel() {

  private val _balanceState by lazy {
    MutableStateFlow<ComponentState<String>>(ComponentState.Loading.FromEmpty)
  }
  val balanceState: StateFlow<ComponentState<String>> by lazy { _balanceState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeBalance() }
  }

  private fun observeBalance() {
    observeBalanceUseCase()
      .onEach { balance ->
        _balanceState.value = ComponentState.Success(DecimalFormat("$#0.00").format(balance))
      }
      .launchIn(viewModelScope)
  }

}