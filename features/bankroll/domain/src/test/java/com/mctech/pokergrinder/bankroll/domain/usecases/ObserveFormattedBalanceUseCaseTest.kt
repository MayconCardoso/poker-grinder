package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.common_test.TestScenario
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions
import org.junit.Test

class ObserveFormattedBalanceUseCaseTest {
  private val observeBalanceUseCase = mockk<ObserveBalanceUseCase>(relaxed = true)
  private val target = ObserveFormattedBalanceUseCase(
    observeBalanceUseCase = observeBalanceUseCase,
  )

  @Test
  fun `should delegate repository flow`() = TestScenario.responseScenario<Flow<String>> {
    val flow = flow {
      emit(500.0)
    }

    givenScenario {
      every { observeBalanceUseCase() } returns flow
    }

    whenAction {
      target.invoke()
    }

    thenAssert { result ->
      Assertions.assertThat(result.last()).isEqualTo("$500.00")
    }
  }
}