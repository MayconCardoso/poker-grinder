package com.mctech.pokergrinder.database.migrations

import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory
import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory.DATABASE_NAME
import org.junit.Rule
import org.junit.Test

internal class PokerGrinderDatabaseMigrationFrom03To04Test {

  @get:Rule
  val helper = PokerGrinderMigrationTestHelperFactory.create()

  @Test
  fun check_migration_03_04() {
    // Create DB with version 1
    helper.createDatabase(DATABASE_NAME, 3)

    // MigrationTestHelper automatically verifies the schema changes
    helper.runMigrationsAndValidate(DATABASE_NAME,
      4,
      true,
      POKER_GRINDER_DATABASE_MIGRATION_03_TO_04)
  }
}
