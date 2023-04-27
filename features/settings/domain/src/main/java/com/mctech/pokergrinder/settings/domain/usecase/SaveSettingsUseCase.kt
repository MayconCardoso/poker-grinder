package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import javax.inject.Inject

/**
 * Used to observe a specific settings.
 *
 * @property repository settings data repository.
 */
class SaveSettingsUseCase @Inject constructor(private val repository: SettingsRepository) {
  suspend operator fun invoke(vararg settings: Settings) {
    settings.forEach { item ->
      repository.save(item)
    }
  }
}