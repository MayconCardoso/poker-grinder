package com.mctech.pokergrinder.database.migrations

import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory
import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory.DATABASE_NAME
import org.junit.Rule
import org.junit.Test

internal class PokerGrinderDatabaseMigrationFrom04To05Test {

  @get:Rule
  val helper = PokerGrinderMigrationTestHelperFactory.create()

  @Test
  fun check_migration_04_05() {
    // Create DB with version 1
    helper.createDatabase(DATABASE_NAME, 4)

    // MigrationTestHelper automatically verifies the schema changes
    helper.runMigrationsAndValidate(DATABASE_NAME,
      5,
      true,
      POKER_GRINDER_DATABASE_MIGRATION_04_TO_05)
  }
}
