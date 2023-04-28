package com.mctech.pokergrinder.backup.presentation

import com.mctech.pokergrinder.backup.domain.entities.Backup

data class BackupComponentState(
  val isShowingConfirmationDialog: Boolean,
  val availableBackups: List<Backup>
)