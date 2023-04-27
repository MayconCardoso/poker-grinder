package com.mctech.pokergrinder.summary.presentation.investment

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import com.mctech.pokergrinder.summary.domain.usecases.ObserveInvestmentSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SummaryInvestmentViewModel @Inject constructor(
  private val observeInvestmentSummaryUseCase: ObserveInvestmentSummaryUseCase,
  private val observeSettingsUseCase: ObserveSettingsUseCase,
) : BaseViewModel() {

  private val _investmentState by lazy {
    MutableStateFlow<ComponentState<SummaryInvestmentState>>(ComponentState.Loading.FromEmpty)
  }
  val investmentState: StateFlow<ComponentState<SummaryInvestmentState>> by lazy { _investmentState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeInvestmentSummary() }
  }

  private fun observeInvestmentSummary() {
    observeInvestmentSummaryUseCase()
      .combine(observeSettingsUseCase(SettingsAvailable.HIDE_BANKROLL_BALANCE)) { summary, settings ->
        val shouldHideBalance = settings?.asBoolean() == true
        SummaryInvestmentState(
          roi = summary.formattedRoi(),
          profit = summary.profit,
          cash = if(shouldHideBalance) "*****" else summary.formattedCash(),
          buyIn = if(shouldHideBalance) "*****" else summary.formattedBuyIn(),
          formattedProfit = if(shouldHideBalance) "*****" else summary.formattedProfit(),
        )
      }
      .onEach { summary ->
        _investmentState.value = ComponentState.Success(summary)
      }
      .launchIn(viewModelScope)
  }

}