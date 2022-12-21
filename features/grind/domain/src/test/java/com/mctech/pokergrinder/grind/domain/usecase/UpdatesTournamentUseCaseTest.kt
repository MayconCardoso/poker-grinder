package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.DepositUseCase
import com.mctech.pokergrinder.bankroll.domain.usecases.UpdateTransactionUseCase
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.testing.newTournament
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.Test

class UpdatesTournamentUseCaseTest {
  private val repository = mockk<GrindRepository>(relaxed = true)
  private val depositUseCase = mockk<DepositUseCase>(relaxed = true)
  private val updateTransactionUseCase = mockk<UpdateTransactionUseCase>(relaxed = true)

  private val target = UpdatesTournamentUseCase(
    repository = repository,
    depositUseCase = depositUseCase,
    updateTransactionUseCase = updateTransactionUseCase,
  )

  @Test(expected = RuntimeException::class)
  fun `should throw error when session not found`() = simpleScenario {
    val tournament = newTournament(id = "")

    whenAction {
      target(tournament)
    }
  }

  @Test
  fun `should update buy in transaction`() = simpleScenario {
    val tournament = newTournament(idSession = "", idTransactionBuyIn = "10", buyIn = 100.0)

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerify { updateTransactionUseCase(id = "10", amount = 100.0) }
    }
  }

  @Test
  fun `should not create a profit transaction when profit does not exist`() = simpleScenario {
    val tournament = newTournament(
      idSession = "",
      idTransactionBuyIn = "10",
      idTransactionProfit = null,
      buyIn = 100.0,
    )

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerifyOrder {
        updateTransactionUseCase(id = "10", amount = 100.0)
        repository.saveGrindTournament(tournament)
      }

      confirmVerified(updateTransactionUseCase, repository, depositUseCase)
    }
  }

  @Test
  fun `should remove profit transaction when new data does not have profit`() = simpleScenario {
    val tournament = newTournament(
      idSession = "",
      idTransactionBuyIn = "10",
      idTransactionProfit = "100",
      buyIn = 100.0,
    )

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerifyOrder {
        updateTransactionUseCase(id = "10", amount = 100.0)
        updateTransactionUseCase(id = "100", amount = 0.0)
        repository.saveGrindTournament(tournament)
      }

      confirmVerified(updateTransactionUseCase, repository, depositUseCase)
    }
  }

  @Test
  fun `should create a profit transaction`() = simpleScenario {
    val tournament = newTournament(
      idSession = "",
      idTransactionBuyIn = "10",
      idTransactionProfit = null,
      buyIn = 100.0,
      profit = 50.0,
    )

    givenScenario {
      coEvery { depositUseCase(any(), any(), any()) } returns "1222"
    }

    whenAction {
      target(tournament)
    }

    thenAssert {
      coVerifyOrder {
        updateTransactionUseCase(id = "10", amount = 100.0)
        depositUseCase(
          description = tournament.title,
          amount = 50.0,
          type = BankrollTransactionType.PROFIT,
        )
        repository.saveGrindTournament(tournament.copy(idTransactionProfit = "1222"))
      }

      confirmVerified(updateTransactionUseCase, repository, depositUseCase)
    }
  }
}