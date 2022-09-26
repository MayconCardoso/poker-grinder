package com.mctech.pokergrinder.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory.DATABASE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class PokerGrinderDatabaseTest {

  @get:Rule
  val helper = PokerGrinderMigrationTestHelperFactory.create()

  @Test
  fun check_database_build_with_all_migrations_included() {
    // Create earliest version of the database.
    helper.createDatabase(DATABASE_NAME, 1).apply {
      close()
    }

    // Open latest version of the database. Room will validate the schema
    // once all migrations execute.
    Room.databaseBuilder(
      InstrumentationRegistry.getInstrumentation().targetContext,
      PokerGrinderDatabase::class.java,
      DATABASE_NAME
    ).run {
      addMigrations(*PokerGrinderDatabaseMigrations.all)
      build()
    }.let { database ->
      database.openHelper.writableDatabase
      database.close()
    }
  }
}
