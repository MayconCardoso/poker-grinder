package com.mctech.pokergrinder.summary.overall_chart

import androidx.lifecycle.viewModelScope
import com.mctech.chart.money.MoneyVariationEntry
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SummaryOverallPerformanceViewModel @Inject constructor(
  private val observeAllGrindsUseCase: ObserveAllGrindsUseCase,
) : BaseViewModel() {

  private val _performanceState by lazy {
    MutableStateFlow<ComponentState<List<MoneyVariationEntry>>>(ComponentState.Loading.FromEmpty)
  }
  val performanceState: StateFlow<ComponentState<List<MoneyVariationEntry>>> by lazy { _performanceState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeInvestmentSummary() }
  }

  private fun observeInvestmentSummary() {
    observeAllGrindsUseCase()
      .map { sessions ->
        sessions.reversed().map { MoneyVariationEntry(it.balance) }
      }
      .onEach { chartEntities ->
        _performanceState.value = ComponentState.Success(chartEntities)
      }
      .launchIn(viewModelScope)
  }

}