package com.mctech.pokergrinder.settings.data

import com.mctech.pokergrinder.settings.data.database.SettingsDao
import com.mctech.pokergrinder.settings.data.mapper.asBusinessSetting
import com.mctech.pokergrinder.settings.data.mapper.asBusinessSettings
import com.mctech.pokergrinder.settings.data.mapper.asDatabaseTransaction
import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class SettingsRepositoryImpl @Inject constructor(
  private val dao: SettingsDao,
) : SettingsRepository {

  override fun observeSettings(): Flow<List<Settings>> {
    return dao.observeAll().map { it.asBusinessSettings() }
  }

  override fun observeSetting(key: String): Flow<Settings> {
    return dao.observe(key).map { it.asBusinessSetting() }
  }

  override suspend fun save(settings: Settings) {
    dao.save(settings.asDatabaseTransaction())
  }

}