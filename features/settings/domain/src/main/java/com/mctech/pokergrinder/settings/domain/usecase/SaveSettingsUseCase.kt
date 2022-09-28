package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(private val repository: SettingsRepository) {
  suspend operator fun invoke(settings: List<Settings>) {
    settings.forEach { item ->
      repository.save(item)
    }
  }
}