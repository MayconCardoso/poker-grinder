package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_04_TO_05 by lazy {
  object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Creates settings table
      database.execSQL(
        """
          CREATE TABLE IF NOT EXISTS `grind_session_tournament_flip` (`id` TEXT NOT NULL, `idSession` TEXT NOT NULL, `tournament` TEXT NOT NULL, `heroHand` TEXT NOT NULL, `villainHand` TEXT NOT NULL, `board` TEXT NOT NULL, `won` INTEGER NOT NULL, PRIMARY KEY(`id`))
        """.trimIndent()
      )

      // Creates index
      database.execSQL(
        """
          CREATE INDEX IF NOT EXISTS `grind_session_tournament_flip_session_index` ON `grind_session_tournament_flip` (`idSession`)
        """.trimIndent()
      )
    }
  }
}
