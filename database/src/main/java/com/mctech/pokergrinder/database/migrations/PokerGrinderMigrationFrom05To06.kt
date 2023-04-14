package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_05_TO_06 by lazy {
  object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Creates settings table
      database.execSQL(
        """
          CREATE TABLE IF NOT EXISTS `range_practice` (`id` TEXT NOT NULL, `cards` TEXT NOT NULL, `action` TEXT NOT NULL, `heroPosition` TEXT NOT NULL, `villainPosition` TEXT NOT NULL, `effectiveStack` INTEGER NOT NULL, `isAnswerCorrect` INTEGER NOT NULL, `dateInMs` INTEGER NOT NULL, PRIMARY KEY(`id`))
        """.trimIndent()
      )

      // Creates index
      database.execSQL(
        """
          CREATE INDEX IF NOT EXISTS `range_practice_hero_position_index` ON `range_practice` (`heroPosition`)
        """.trimIndent()
      )

      // Creates index
      database.execSQL(
        """
          CREATE INDEX IF NOT EXISTS `range_practice_stack_index` ON `range_practice` (`effectiveStack`)
        """.trimIndent()
      )
    }
  }
}
