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

class ObserveAllGrindsUseCaseTest {

  private val repository = mockk<GrindRepository>(relaxed = true)
  private val target = ObserveAllGrindsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<Session>>> {
      val items = listOf(
        newSession(id = "1"),
        newSession(id = "2"),
      )

      val flow = flow { emit(items) }

      givenScenario {
        every { repository.observeAllGrinds() } returns flow
      }

      whenAction {
        target.invoke()
      }

      thenAssert { result ->
        assertThat(result).isEqualTo(flow)
        assertThat(result.last()).isEqualTo(items)
      }
    }
}