package com.mctech.pokergrinder.settings.domain

import com.mctech.pokergrinder.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow

/**
 * Settings repository to access transaction data.
 */
interface SettingsRepository {

  /**
   * Observes all settings.
   */
  fun observeSettings(): Flow<List<Settings>>

  /**
   * Observes an specific setting given its [key].
   */
  fun observeSetting(key: String): Flow<Settings?>

  /**
   * Saves a [settings].
   */
  suspend fun save(settings: Settings)
}