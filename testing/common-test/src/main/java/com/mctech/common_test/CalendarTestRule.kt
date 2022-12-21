package com.mctech.common_test

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.Calendar

/**
 * Test rule to make it possible to mock a timeStamp value.
 */
class CalendarTestRule : TestRule {

  /**
   * Available calendar that can be mocked.
   */
  val calendar = mockk<Calendar>(relaxed = true)

  /**
   * Runs the rule behaviour.
   */
  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        // Before each test.
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar

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
