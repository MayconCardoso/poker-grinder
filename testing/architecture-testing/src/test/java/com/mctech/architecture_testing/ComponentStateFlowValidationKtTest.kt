package com.mctech.architecture_testing

import com.mctech.architecture_testing.extensions.assertFlow
import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.architecture.ComponentState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class ComponentStateFlowValidationKtTest {
  private val expectedValue = "Hello world"
  private val expectedError = RuntimeException()

  @Test
  fun `should assert success flow`() = responseScenario<List<ComponentState<String>>> {
    val expectedSuccessFLow = listOf(
      ComponentState.Loading.FromEmpty,
      ComponentState.Success(expectedValue)
    )

    whenAction {
      expectedSuccessFLow
    }

    thenAssert { stateFlow ->
      stateFlow.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedValue)
      )
    }
  }

  @Test
  fun `should assert error flow`() = responseScenario<List<ComponentState<String>>> {
    val expectedErrorFLow = listOf<ComponentState<String>>(
      ComponentState.Loading.FromEmpty,
      ComponentState.Error(expectedError)
    )

    whenAction {
      expectedErrorFLow
    }

    thenAssert { stateFlow ->
      stateFlow.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Error<String>(expectedError)
      )
    }
  }
}
