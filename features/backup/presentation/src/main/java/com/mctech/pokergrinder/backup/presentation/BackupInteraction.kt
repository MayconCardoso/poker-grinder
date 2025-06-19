package com.mctech.pokergrinder.backup.presentation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.backup.domain.entities.Backup

/**
 * Defines the available events that can be sent from the feature.
 */
sealed class BackupInteraction: UserInteraction {

  /**
   * Indicates user has clicked the backup button.
   */
  object OnBackupButtonClicked : BackupInteraction()

  /**
   * Indicates user has clicked the backup button.
   */
  object OnRestoreBackupConfirmed : BackupInteraction()

  /**
   * Indicates user has clicked the backup button.
   */
  object OnRestoreConfirmationRemoved : BackupInteraction()

  /**
   * Indicates user has clicked the withdraw button.
   */
  data class OnBackupClicked(val backup: Backup) : BackupInteraction()
}