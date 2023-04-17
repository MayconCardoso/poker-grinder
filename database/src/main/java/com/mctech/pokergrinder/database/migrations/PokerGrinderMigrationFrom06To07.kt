package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_06_TO_07 by lazy {
  object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Drop table
      database.execSQL("DROP TABLE IF EXISTS `range_practice`")

      // Creates settings table
      database.execSQL(
        """
          CREATE TABLE IF NOT EXISTS `range_practice` (`id` TEXT NOT NULL, `cards` TEXT NOT NULL, `action` TEXT NOT NULL, `heroPosition` TEXT NOT NULL, `villainPosition` TEXT, `effectiveStack` INTEGER NOT NULL, `isAnswerCorrect` INTEGER NOT NULL, `dateInMs` INTEGER NOT NULL, PRIMARY KEY(`id`))
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
