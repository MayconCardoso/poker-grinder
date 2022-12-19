package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.testing.newSession
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveCurrentGrindUseCaseTest {

  private val repository = mockk<GrindRepository>(relaxed = true)
  private val target = ObserveCurrentGrindUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<Session?>> {
    val flow = flow { emit(newSession(id = "1")) }

    givenScenario {
      every { repository.observeCurrentGrind() } returns flow
    }

    whenAction {
      target.invoke()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(newSession(id = "1"))
    }
  }
}