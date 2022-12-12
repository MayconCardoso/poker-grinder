package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.newTournament
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class SavesTournamentUseCaseTest {

  private val repository = mockk<TournamentRepository>(relaxed = true)
  private val generateUniqueIdUseCase = mockk<GenerateUniqueIdUseCase>(relaxed = true)

  private val target = SavesTournamentUseCase(
    repository = repository,
    generateUniqueIdUseCase = generateUniqueIdUseCase,
  )

  @Test
  fun `should update existent tournament`() = simpleScenario {
    val tournament = newTournament(id = "1")

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerifyOrder {
        repository.save(tournament)
      }

      confirmVerified(repository, generateUniqueIdUseCase)
    }
  }


  @Test
  fun `should create new tournament`() = simpleScenario {
    val tournament = newTournament(id = "")
    val savedTournament = newTournament(id = "2")

    givenScenario {
      every { generateUniqueIdUseCase() } returns "2"
    }

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerifyOrder {
        generateUniqueIdUseCase()
        repository.save(savedTournament)
      }

      confirmVerified(repository, generateUniqueIdUseCase)
    }
  }

}