package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe settings.
 *
 * @property repository settings data repository.
 */
class ObserveAllSettingsUseCase @Inject constructor(private val repository: SettingsRepository) {
  operator fun invoke(): Flow<List<Settings>> {
    return repository.observeSettings()
  }
}