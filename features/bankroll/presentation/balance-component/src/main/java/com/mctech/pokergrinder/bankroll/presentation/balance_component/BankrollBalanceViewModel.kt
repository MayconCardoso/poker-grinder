package com.mctech.pokergrinder.bankroll.presentation.balance_component

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.bankroll.domain.usecases.ObserveFormattedBalanceUseCase
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import com.mctech.pokergrinder.settings.domain.usecase.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class BankrollBalanceViewModel @Inject constructor(
  private val observeBalanceUseCase: ObserveFormattedBalanceUseCase,
  private val saveSettingsUseCase: SaveSettingsUseCase,
  private val observeSettingsUseCase: ObserveSettingsUseCase,
) : BaseViewModel() {

  /**
   * Holds the hide balance settings.
   */
  private var hideBalanceSettings: Settings? = null

  private val _balanceState by lazy {
    MutableStateFlow<ComponentState<String>>(ComponentState.Loading.FromEmpty)
  }
  val balanceState: StateFlow<ComponentState<String>> by lazy { _balanceState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeBalance() }
  }

  private fun observeBalance() {
    observeBalanceUseCase()
      .combine(observeSettingsUseCase(SettingsAvailable.HIDE_BANKROLL_BALANCE)) { balance, settings ->
        // Hold settings for future usage.
        this.hideBalanceSettings = settings

        // Decided if balance will be shown based on the settings value.
        if (settings?.asBoolean() == true) {
          "******"
        } else {
          balance
        }
      }
      .onEach { balance ->
        _balanceState.value = ComponentState.Success(balance)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(BankrollBalanceInteraction.OnBalanceClicked::class)
  private suspend fun onBalanceClicked() {
    // Gets the current settings
    val settings = hideBalanceSettings ?: Settings(
      key = SettingsAvailable.HIDE_BANKROLL_BALANCE.key,
      value = "false",
      title = "Hide the bankroll balance",
    )

    // Inverts value
    saveSettingsUseCase(
      settings.copy(
        value = (!settings.asBoolean()).toString()
      )
    )
  }

}