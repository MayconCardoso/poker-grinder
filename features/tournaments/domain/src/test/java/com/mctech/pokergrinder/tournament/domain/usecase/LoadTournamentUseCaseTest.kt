package com.mctech.pokergrinder.tournament.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.tournament.domain.TournamentRepository
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.newTournament
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LoadTournamentUseCaseTest {

  private val repository = mockk<TournamentRepository>(relaxed = true)
  private val target = LoadTournamentUseCase(
    repository = repository,
  )

  @Test
  fun `should return null when tournament not found`() = responseScenario<Tournament?> {
    givenScenario {
      coEvery { repository.load(any()) } returns null
    }

    whenAction {
      target.invoke("Sit and Go $25")
    }

    thenAssert { result ->
      assertThat(result).isNull()
      coVerifyOrder {
        repository.load("Sit and Go $25")
      }
      confirmVerified(repository)
    }
  }

  @Test
  fun `should return tournament`() = responseScenario<Tournament?> {
    givenScenario {
      coEvery { repository.load(any()) } returns newTournament(id = "1")
    }

    whenAction {
      target.invoke("Sit and Go $25")
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(newTournament(id = "1"))
      coVerifyOrder {
        repository.load("Sit and Go $25")
      }
      confirmVerified(repository)
    }
  }
}