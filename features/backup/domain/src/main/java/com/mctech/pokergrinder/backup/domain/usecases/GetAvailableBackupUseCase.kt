package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.Backup
import javax.inject.Inject

/**
 * Responsible for showing all available data in the app.
 */
class GetAvailableBackupUseCase @Inject constructor(
  private val repository: BackupRepository,
) {
  suspend operator fun invoke(): List<Backup> {
    return repository.loadAvailableBackup()
  }
}