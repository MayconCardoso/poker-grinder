package com.mctech.pokergrinder.database

import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_01_TO_02
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_02_TO_03
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_03_TO_04
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_04_TO_05
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_05_TO_06
import com.mctech.pokergrinder.database.migrations.POKER_GRINDER_DATABASE_MIGRATION_06_TO_07

internal object PokerGrinderDatabaseMigrations {

  val all by lazy {
    arrayOf(
      POKER_GRINDER_DATABASE_MIGRATION_01_TO_02,
      POKER_GRINDER_DATABASE_MIGRATION_02_TO_03,
      POKER_GRINDER_DATABASE_MIGRATION_03_TO_04,
      POKER_GRINDER_DATABASE_MIGRATION_04_TO_05,
      POKER_GRINDER_DATABASE_MIGRATION_05_TO_06,
      POKER_GRINDER_DATABASE_MIGRATION_06_TO_07,
    )
  }
}
