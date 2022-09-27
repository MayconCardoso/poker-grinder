package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_03_TO_04 by lazy {
  object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Creates settings table
      database.execSQL(
        """
        CREATE TABLE IF NOT EXISTS `settings` (`settingKey` TEXT NOT NULL, `settingValue` TEXT NOT NULL, `settingTitle` TEXT NOT NULL, PRIMARY KEY(`settingKey`))
        """.trimIndent()
      )

      // Insert default settings
      database.execSQL(
        """
          INSERT INTO `settings` (`settingKey`, `settingValue`, `settingTitle`) VALUES ('group_session_tournaments', '0', 'Group similar session tournament')
        """.trimIndent()
      )
    }
  }
}
