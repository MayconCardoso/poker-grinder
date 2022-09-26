package com.mctech.pokergrinder.database

import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_01_TO_02
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_02_TO_03

internal object PokerGrinderDatabaseMigrations {

  val all by lazy {
    arrayOf(
      POKER_GRINDER_DATABASE_MIGRATION_01_TO_02,
      POKER_GRINDER_DATABASE_MIGRATION_02_TO_03,
    )
  }
}
