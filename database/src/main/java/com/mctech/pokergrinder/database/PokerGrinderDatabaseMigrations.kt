package com.mctech.pokergrinder.database

import androidx.room.migration.Migration
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_01_TO_02

internal object PokerGrinderDatabaseMigrations {

  val all by lazy {
    arrayOf(POKER_GRINDER_DATABASE_MIGRATION_01_TO_02)
  }
}
