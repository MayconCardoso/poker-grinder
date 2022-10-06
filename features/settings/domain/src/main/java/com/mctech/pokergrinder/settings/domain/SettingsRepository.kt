package com.mctech.pokergrinder.settings.domain

import com.mctech.pokergrinder.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
  fun observeSettings(): Flow<List<Settings>>

  fun observeSetting(key: String): Flow<Settings>

  suspend fun save(settings: Settings)
}