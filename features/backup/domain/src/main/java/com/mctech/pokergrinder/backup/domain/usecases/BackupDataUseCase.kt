package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Responsible for creating a backup of the saved data.
 */
class BackupDataUseCase @Inject constructor(
  private val repository: BackupRepository,
) {
  operator fun invoke(): Flow<BackupState> {
    return repository.backupData()
  }
}