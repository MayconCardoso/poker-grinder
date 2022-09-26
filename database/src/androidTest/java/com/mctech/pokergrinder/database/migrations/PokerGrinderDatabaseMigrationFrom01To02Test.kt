package com.mctech.pokergrinder.database.migrations

import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory
import com.mctech.pokergrinder.database.PokerGrinderMigrationTestHelperFactory.DATABASE_NAME
import org.junit.Rule
import org.junit.Test

internal class PokerGrinderDatabaseMigrationFrom01To02Test {

  @get:Rule
  val helper = PokerGrinderMigrationTestHelperFactory.create()

  @Test
  fun check_migration_01_02() {
    // Create DB with version 1
    helper.createDatabase(DATABASE_NAME, 1)

    // MigrationTestHelper automatically verifies the schema changes
    helper.runMigrationsAndValidate(DATABASE_NAME,
      2,
      true,
      POKER_GRINDER_DATABASE_MIGRATION_01_TO_02)
  }
}
