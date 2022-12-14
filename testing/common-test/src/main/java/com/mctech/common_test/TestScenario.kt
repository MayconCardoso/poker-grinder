package com.mctech.common_test

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

/**
 * Function to make sure we are testing our unit tests separately.
 * It also makes the test readability clearer once it splits your test by blocks.
 *
 * In shorts, by calling one of those functions [simpleScenario] or [responseScenario] you will
 * be creating a functional block where you can:
 *    - Declare variables inside
 *    - Create and call your own functions to be executed inside the test scope.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TestScenario<T> {
  private var action: (suspend () -> T)? = null
  private var scenario: (suspend () -> Unit)? = null
  private var assertion: (suspend (T) -> Unit)? = null

  /**
   * This is a optional block where you will prepare your test data, mock objects if needed, etc.
   * You cannot have more then one call for this method on your test.
   */
  fun givenScenario(scenario: suspend () -> Unit) {
    if (this.scenario != null) {
      throw IllegalArgumentException("You must have only one scenario by test.")
    }

    this.scenario = scenario
  }

  /**
   * This is a required block where you will call the function that will trigger your test.
   * In a perfect world some thing must happen to you assert the result of it.
   */
  fun whenAction(action: suspend () -> T) {
    if (this.action != null) {
      throw IllegalArgumentException("You must have only one action by test.")
    }

    this.action = action
  }

  /**
   * This is a required block where you will assert the result of your test and make sure
   * all the expected results were achieved.
   */
  fun thenAssert(assert: suspend (T) -> Unit) {
    if (this.assertion != null) {
      throw IllegalArgumentException("You must have only one assertion block by test.")
    }

    assertion = assert
  }

  // if 'java.lang.IllegalStateException: This job has not completed yet' occur
  // please consider using '***General' methods which uses 'executeBlocking()'
  private fun executeBlockingTest() = runTest { execute() }

  // Retrofit2 uses threads so it's not possible to use 'runBlockingTest'
  // https://github.com/square/retrofit/issues/3330
  private fun executeBlocking() = runBlocking { execute() }

  private suspend fun execute() {
    if (action == null) {
      throw IllegalArgumentException("You must have an action block. Call whenAction{}")
    }

    if (assertion == null) {
      throw IllegalArgumentException("You must have an assertion block. Call thenAssert{}")
    }

    // Prepare test
    scenario?.invoke()

    // Call the action trigger
    val actionResult = action?.invoke()

    // Assertions
    assertion?.invoke(requireNotNull(actionResult))
  }

  companion object {
    /**
     * Call this function when your action doesn't return any value to be asserted.
     *
     *  E.G.
     *
     *  @Test
     *  fun yourFunction() = simpleScenario {
     *    givenScenario {
     *      whenever(logger.itemSavedOnDb()).thenReturn(Result.Success)
     *    }
     *
     *    whenAction {
     *      bar.saveOnDb()
     *    }
     *
     *    thenAssert {
     *      verify(logger).itemSavedOnDb()
     *    }
     *  }
     */
    fun simpleScenario(testScopeBlock: TestScenario<Unit>.() -> Unit) {
      TestScenario<Unit>()
        .apply(testScopeBlock)
        .executeBlockingTest()
    }

    /**
     * @see simpleScenario
     *
     * This method is the same but uses 'runBlocking' instead of 'runBlockingTest'
     */
    fun simpleScenarioGeneral(testScopeBlock: TestScenario<Unit>.() -> Unit) {
      TestScenario<Unit>()
        .apply(testScopeBlock)
        .executeBlocking()
    }

    /**
     * Call this function when your action returns some value to be asserted.
     *
     *  E.G.
     *
     *  @Test
     *  fun yourFunction() = responseScenario {
     *    givenScenario {
     *      whenever(repository.loadAllData()).thenReturn(expectedItems)
     *    }
     *
     *    whenAction {
     *      presenter.loadAllData()
     *    }
     *
     *    thenAssert { allData ->
     *      verify(repository).loadAllData()
     *      assertThat(allData).isEqualTo(expectedItems)
     *    }
     *  }
     */
    fun <T> responseScenario(testScopeBlock: TestScenario<T>.() -> Unit) {
      TestScenario<T>()
        .apply(testScopeBlock)
        .executeBlockingTest()
    }

    /**
     * @see responseScenario
     *
     * This method is the same but uses 'runBlocking' instead of 'runBlockingTest'
     */
    fun <T> responseScenarioGeneral(testScopeBlock: TestScenario<T>.() -> Unit) {
      TestScenario<T>()
        .apply(testScopeBlock)
        .executeBlocking()
    }
  }
}
