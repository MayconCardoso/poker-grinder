package com.mctech.common_test

import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.Calendar
import java.util.UUID

/**
 * Test rule to make it possible to mock a timeStamp value.
 */
class UuidTestRule : TestRule {

  /**
   * Runs the rule behaviour.
   */
  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        // Before each test.
        mockkStatic(UUID::class)

        try {
          // Run the test
          base.evaluate()
        } finally {
          // After each test.
          unmockkStatic(Calendar::class)
        }
      }
    }
  }

}
