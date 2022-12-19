package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.testing.newSessionFlip
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveGrindTournamentFlipsUseCaseTest {

  private val repository = mockk<GrindRepository>(relaxed = true)
  private val target = ObserveGrindTournamentFlipsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<SessionTournamentFlip>>> {
    val items = listOf(
      newSessionFlip(id = "1"),
      newSessionFlip(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observeGrindTournamentFlips(any()) } returns flow
    }

    whenAction {
      target.invoke("10")
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(items)
    }
  }
}