package com.mctech.pokergrinder.grind_tournament.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GroupGrindTournamentUseCaseTest {
  private val target = GroupGrindTournamentUseCase()
  private val tournaments = listOf(
    newTournament(
      id = "1",
      idTransactionBuyIn = "10",
      title = "Tournament 01",
      buyIn = 11.00,
      profit = 55.00,
      startTimeInMs = 1
    ),
    newTournament(
      id = "2",
      idTransactionBuyIn = "10",
      title = "Tournament 01",
      buyIn = 11.00,
      profit = 0.00,
      startTimeInMs = 2
    ),
    newTournament(
      id = "3",
      idTransactionBuyIn = "10",
      title = "Tournament 01",
      buyIn = 11.00,
      profit = 0.00,
      startTimeInMs = 3
    ),
    newTournament(
      id = "4",
      idTransactionBuyIn = "10",
      title = "Tournament 02",
      buyIn = 109.00,
      profit = 0.00,
      startTimeInMs = 1
    ),
    newTournament(
      id = "5",
      idTransactionBuyIn = "10",
      title = "Tournament 02",
      buyIn = 109.00,
      profit = 50.00,
      startTimeInMs = 2
    ),
    newTournament(
      id = "6",
      idTransactionBuyIn = "10",
      title = "Tournament 03",
      buyIn = 1050.00,
      profit = 0.00,
      startTimeInMs = 1
    ),
  )

  @Test
  fun `should group same tournaments`() = responseScenario<List<SessionTournament>> {
    val expected = listOf(
      newTournament(
        id = "",
        idTransactionBuyIn = "",
        idTransactionProfit = null,
        title = "Tournament 01",
        buyIn = 33.00,
        profit = 55.00,
        startTimeInMs = 3,
        isGrouped = true,
      ),
      newTournament(
        id = "",
        idTransactionBuyIn = "",
        idTransactionProfit = null,
        title = "Tournament 02",
        buyIn = 218.00,
        profit = 50.00,
        startTimeInMs = 2,
        isGrouped = true,
      ),
      newTournament(
        id = "6",
        idTransactionBuyIn = "10",
        title = "Tournament 03",
        buyIn = 1050.00,
        profit = 0.00,
        startTimeInMs = 1,
        isGrouped = false,
      ),
    )
    whenAction {
      target(tournaments)
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(expected)
    }
  }
}