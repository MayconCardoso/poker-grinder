package com.mctech.pokergrinder.backup.domain.entities

/**
 * Defines a state of the backup job
 */
sealed class BackupState {

  /**
   * Indicates the backup is in progress.
   */
  data class InProgress(val percent: Double) : BackupState()

  /**
   * Indicates the backup could not been finished because of the [issue].
   */
  data class Error(val issue: Throwable) : BackupState()

  /**
   * Indicates the backup has been finishied.
   */
  object Finished : BackupState()
}