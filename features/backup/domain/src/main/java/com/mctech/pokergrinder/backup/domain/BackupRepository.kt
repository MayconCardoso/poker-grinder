package com.mctech.pokergrinder.backup.domain

import com.mctech.pokergrinder.backup.domain.entities.Backup
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import kotlinx.coroutines.flow.Flow

/**
 * Backup repository to access the project data.
 */
interface BackupRepository {

  /**
   * Restores the data from a previous [backup].
   */
  suspend fun restoreData(backup: Backup): Flow<BackupState>

  /**
   * Performs a backup of the saved data.
   */
  suspend fun backupData(): Flow<BackupState>

  /**
   * Loads all available backup data.
   */
  suspend fun loadAvailableBackup(): List<Backup>
}