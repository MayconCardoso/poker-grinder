package com.mctech.pokergrinder.backup.presentation

import com.mctech.pokergrinder.backup.domain.entities.Backup

data class BackupComponentState(
  val message: String,
  val isShowingLoading: Boolean,
  val availableBackups: List<Backup>
)