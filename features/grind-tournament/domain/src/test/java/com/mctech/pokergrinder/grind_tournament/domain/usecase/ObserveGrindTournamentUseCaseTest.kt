package com.mctech.pokergrinder.grind_tournament.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentRepository
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveGrindTournamentUseCaseTest {

  private val repository = mockk<GrindTournamentRepository>(relaxed = true)
  private val target = ObserveGrindTournamentUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<SessionTournament>>> {
    val items = listOf(
      newTournament(id = "1"),
      newTournament(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observeGrindTournament(any()) } returns flow
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