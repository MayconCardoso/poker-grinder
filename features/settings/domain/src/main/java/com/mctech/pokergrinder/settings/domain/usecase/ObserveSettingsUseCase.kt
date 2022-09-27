package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(private val repository: SettingsRepository) {
  operator fun invoke(settings: SettingsAvailable): Flow<Settings> {
    return repository.observeSetting(settings.key)
  }
}