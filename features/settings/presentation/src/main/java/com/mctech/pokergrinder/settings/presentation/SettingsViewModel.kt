package com.mctech.pokergrinder.settings.presentation

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.usecase.ObserveAllSettingsUseCase
import com.mctech.pokergrinder.settings.domain.usecase.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
  private val saveSettingsUseCase: SaveSettingsUseCase,
  private val observeAllSettingsUseCase: ObserveAllSettingsUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Settings>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<Settings>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeAllSettingsUseCase()
      .onEach { settings ->
        _componentState.value = ComponentState.Success(settings)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(SettingsInteraction.OnSettingChanged::class)
  private suspend fun onSettingChanged(interaction: SettingsInteraction.OnSettingChanged) {
    // Gets setting state.
    val settings = (componentState.value as ComponentState.Success).result

    // Get selected setting.
    val selected = settings.firstOrNull { it.key == interaction.setting.key } ?: return

    // Check if settings has changed.
    if (selected.asBoolean() == interaction.value) {
      return
    }

    // Save settings
    saveSettingsUseCase(
      settings.map {
        if (it == selected) {
          it.copy(value = interaction.value.toString())
        } else {
          it
        }
      }
    )
  }
}