package com.mctech.pokergrinder.database.migrations

import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory
import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory.DATABASE_NAME
import org.junit.Rule
import org.junit.Test

internal class PokerGrinderDatabaseMigrationFrom02To03Test {

  @get:Rule
  val helper = PokerGrinderMigrationTestHelperFactory.create()

  @Test
  fun check_migration_02_03() {
    // Create DB with version 1
    helper.createDatabase(DATABASE_NAME, 2)

    // MigrationTestHelper automatically verifies the schema changes
    helper.runMigrationsAndValidate(DATABASE_NAME,
      3,
      true,
      POKER_GRINDER_DATABASE_MIGRATION_02_TO_03)
  }
}
