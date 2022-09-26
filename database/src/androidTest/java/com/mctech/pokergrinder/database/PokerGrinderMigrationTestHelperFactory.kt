package com.mctech.pokergrinder.database

import androidx.room.migration.AutoMigrationSpec
import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry

internal object PokerGrinderMigrationTestHelperFactory {

  const val DATABASE_NAME = "migration-test"

  fun create() = MigrationTestHelper(
    InstrumentationRegistry.getInstrumentation(),
    PokerGrinderDatabase::class.java,
    listOf<AutoMigrationSpec>()
  )
}
