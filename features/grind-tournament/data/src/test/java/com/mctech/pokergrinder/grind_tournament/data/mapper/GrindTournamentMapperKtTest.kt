package com.mctech.pokergrinder.grind_tournament.data.mapper

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind_tournament.data.newDatabaseTournament
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindTournamentMapperKtTest {

  private val sessionTournamentDatabase = newDatabaseTournament(
    id = "1",
    idSession = "100",
    idTransactionProfit = "1000",
    idTransactionBuyIn = "2000",
    title = "Hey there",
    buyIn = 10.0,
    profit = 100.0,
    startTimeInMs = 0,
  )

  private val sessionTournamentBusiness = newTournament(
    id = "1",
    idSession = "100",
    idTransactionProfit = "1000",
    idTransactionBuyIn = "2000",
    title = "Hey there",
    buyIn = 10.0,
    profit = 100.0,
    startTimeInMs = 0,
  )

  @Test
  fun `should convert tournament database list to business list`() =
    responseScenario<List<SessionTournament>> {
      whenAction {
        listOf(sessionTournamentDatabase).asBusinessTournaments()
      }

      thenAssert { result ->
        assertThat(result).isEqualTo(listOf(sessionTournamentBusiness))
      }
    }

  @Test
  fun `should convert tournament database to business`() = responseScenario<SessionTournament> {
    whenAction {
      sessionTournamentDatabase.asBusinessTournaments()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(sessionTournamentBusiness)
    }
  }

  @Test
  fun `should convert tournament business to database`() =
    responseScenario<SessionTournamentRoomEntity> {
      whenAction {
        sessionTournamentBusiness.asDatabaseTournaments()
      }

      thenAssert { result ->
        assertThat(result).isEqualTo(sessionTournamentDatabase)
      }
    }

}