package com.mctech.pokergrinder.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

internal object PokerGrinderDatabaseInitialization : RoomDatabase.Callback() {
  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)

    // Insert first settings.
    db.execSQL(
      """
        INSERT INTO `settings` 
            (`settingKey`, `settingValue`, `settingTitle`) 
        VALUES 
            ('group_session_tournaments', 'false', 'Group similar session tournament')
      """.trimIndent()
    )
  }
}