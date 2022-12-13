package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions
import org.junit.Test

class ObserveBalanceUseCaseTest {
  private val repository = mockk<BankrollRepository>(relaxed = true)
  private val target = ObserveBalanceUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<Double>> {
    val flow = flow {
      emit(500.0)
    }

    givenScenario {
      every { repository.observeBalance() } returns flow
    }

    whenAction {
      target.invoke()
    }

    thenAssert { result ->
      Assertions.assertThat(result).isEqualTo(flow)
      Assertions.assertThat(result.last()).isEqualTo(500.0)
    }
  }
}