package com.mctech.pokergrinder.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val POKER_GRINDER_DATABASE_MIGRATION_02_TO_03 by lazy {
  object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
      // Creates session grind view
      database.execSQL(
        """
        DROP VIEW `grind_session_detail`
        """.trimIndent()
      )

      // Creates session grind view
      database.execSQL(
        """
          CREATE VIEW `grind_session_detail` AS SELECT gs.id, gs.title, gs.isOpened, COUNT(1) tournaments, COALESCE(SUM(buyIn), 0) buyIn, COALESCE(SUM(profit), 0) cash, COALESCE(AVG(buyIn), 0) avgBuyIn, gs.startTimeInMs FROM grind_session gs LEFT JOIN grind_session_tournament gst ON gs.id = gst.idSession GROUP BY gs.id
        """.trimIndent()
      )
    }
  }
}
