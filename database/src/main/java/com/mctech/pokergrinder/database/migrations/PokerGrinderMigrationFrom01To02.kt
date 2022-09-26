package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_01_TO_02 by lazy {
  object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Create new temp table to move data from existent one
      database.execSQL(
        """
        CREATE TABLE IF NOT EXISTS `grind_session_new` (
            `id` TEXT NOT NULL, 
            `title` TEXT NOT NULL, 
            `isOpened` INTEGER NOT NULL, 
            `startTimeInMs` INTEGER NOT NULL, PRIMARY KEY(`id`)
        )
        """.trimIndent()
      )

      // Move data to new one
      database.execSQL(
        """
        INSERT INTO `grind_session_new` (`id`, `title`, `isOpened`, `startTimeInMs`) 
        SELECT `id`, `title`, `isOpened`, `startTimeInMs` FROM grind_session
        """.trimIndent()
      )

      // Drop old table index
      database.execSQL(
        """
          DROP INDEX `grind_session_is_opened_index`
        """.trimIndent()
      )

      // Drop old table
      database.execSQL(
        """
          DROP TABLE `grind_session`
        """.trimIndent()
      )

      // Rename new table
      database.execSQL(
        """
        ALTER TABLE `grind_session_new` RENAME TO `grind_session`
        """.trimIndent()
      )

      // Creates new index
      database.execSQL(
        """
         CREATE INDEX IF NOT EXISTS `grind_session_is_opened_index` ON `grind_session` (`isOpened`)
        """.trimIndent()
      )

      // Creates session grind view
      database.execSQL(
        """
        CREATE VIEW `grind_session_detail` AS SELECT gs.id, gs.title, gs.isOpened, COUNT(1) tournaments, SUM(buyIn) buyIn, SUM(profit) cash, gs.startTimeInMs FROM grind_session_tournament gst INNER JOIN grind_session gs ON gs.id = gst.idSession GROUP  BY idSession
        """.trimIndent()
      )
    }
  }
}
