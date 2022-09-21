package com.mctech.pokergrind.threading

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrind.threading.DefaultCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DefaultCoroutineDispatchersTest {
  @Test
  fun `should assert io dispatcher`() = responseScenario<CoroutineDispatcher> {
    whenAction {
      DefaultCoroutineDispatchers.io
    }

    thenAssert {
      assertThat(it).isEqualTo(Dispatchers.IO)
    }
  }

  @Test
  fun `should assert main dispatcher`() = responseScenario<CoroutineDispatcher> {
    whenAction {
      DefaultCoroutineDispatchers.main
    }

    thenAssert {
      assertThat(it).isEqualTo(Dispatchers.Main)
    }
  }

  @Test
  fun `should assert computation dispatcher`() = responseScenario<CoroutineDispatcher> {
    whenAction {
      DefaultCoroutineDispatchers.default
    }

    thenAssert {
      assertThat(it).isEqualTo(Dispatchers.Default)
    }
  }
}
