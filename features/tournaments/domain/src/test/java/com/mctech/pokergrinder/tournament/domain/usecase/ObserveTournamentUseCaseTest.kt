package com.mctech.pokergrinder.tournament.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.tournament.domain.TournamentRepository
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.newTournament
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveTournamentUseCaseTest {

  private val repository = mockk<TournamentRepository>(relaxed = true)
  private val target = ObserveTournamentUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<Tournament>>> {
    val items = listOf(
      newTournament(id = "1"),
      newTournament(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observe() } returns flow
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