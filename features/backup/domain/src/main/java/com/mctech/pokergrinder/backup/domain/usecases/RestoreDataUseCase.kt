package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.Backup
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Responsible for restoring a backup of the saved data.
 */
class RestoreDataUseCase @Inject constructor(
  private val repository: BackupRepository,
) {
  suspend operator fun invoke(backup: Backup): Flow<BackupState> {
    return repository.restoreData(backup)
  }
}