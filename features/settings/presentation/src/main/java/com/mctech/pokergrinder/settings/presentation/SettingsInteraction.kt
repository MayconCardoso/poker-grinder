package com.mctech.pokergrinder.settings.presentation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable

internal sealed class SettingsInteraction : UserInteraction {
  data class OnSettingChanged(
    val setting: SettingsAvailable,
    val value: Boolean,
  ) : SettingsInteraction()
}