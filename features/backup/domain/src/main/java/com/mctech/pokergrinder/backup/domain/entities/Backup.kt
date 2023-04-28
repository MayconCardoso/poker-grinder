package com.mctech.pokergrinder.backup.domain.entities

/**
 * Represents a backup entity.
 */
data class Backup(
  val title: String,
  val filePath: String,
  val sizeInMb: Double,
)