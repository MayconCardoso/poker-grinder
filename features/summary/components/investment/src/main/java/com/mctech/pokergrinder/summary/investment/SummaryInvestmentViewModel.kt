package com.mctech.pokergrinder.summary.investment

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.summary.domain.entities.InvestmentSummary
import com.mctech.pokergrinder.summary.domain.usecases.ObserveInvestmentSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SummaryInvestmentViewModel @Inject constructor(
  private val observeInvestmentSummaryUseCase: ObserveInvestmentSummaryUseCase,
) : BaseViewModel() {

  private val _investmentState by lazy {
    MutableStateFlow<ComponentState<InvestmentSummary>>(ComponentState.Loading.FromEmpty)
  }
  val investmentState: StateFlow<ComponentState<InvestmentSummary>> by lazy { _investmentState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeInvestmentSummary() }
  }

  private fun observeInvestmentSummary() {
    observeInvestmentSummaryUseCase()
      .onEach { summary ->
        _investmentState.value = ComponentState.Success(summary)
      }
      .launchIn(viewModelScope)
  }

}