package com.mctech.pokergrinder.grind.data.mapper

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind.data.newDatabaseSession
import com.mctech.pokergrinder.grind.data.newDatabaseSessionDetail
import com.mctech.pokergrinder.grind.data.newDatabaseSessionFlip
import com.mctech.pokergrinder.grind.data.newDatabaseTournament
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newSessionFlip
import com.mctech.pokergrinder.grind.testing.newTournament
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindMapperKtTest {

  private val sessionDetailDatabase = newDatabaseSessionDetail(
    id = "10",
    title = "Hello World",
    isOpened = true,
    startTimeInMs = 100000,
    cash = 1000.0,
    buyIn = 500.0,
    avgBuyIn = 10.0,
    tournamentsPlayed = 100,
  )

  private val sessionDatabase = newDatabaseSession(
    id = "10",
    title = "Hello World",
    isOpened = true,
    startTimeInMs = 100000,
  )

  private val sessionBusiness = newSession(
    id = "10",
    title = "Hello World",
    isOpened = true,
    startTimeInMs = 100000,
    cash = 1000.0,
    buyIn = 500.0,
    avgBuyIn = 10.0,
    tournamentsPlayed = 100,
  )

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

  private val sessionFlipDatabase = newDatabaseSessionFlip(
    id = "1",
    won = true,
    idSession = "100",
    tournament = "Hey there",
    board = "acacacacacac",
    heroHand = "kskh",
    villainHand = "asah",
  )

  private val sessionFlipBusiness = newSessionFlip(
    id = "1",
    won = true,
    idSession = "100",
    tournament = "Hey there",
    board = "acacacacacac",
    heroHand = "kskh",
    villainHand = "asah",
  )

  @Test
  fun `should convert session database list to business list`() = responseScenario<List<Session>> {
    whenAction {
      listOf(sessionDetailDatabase).asBusinessSessions()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(listOf(sessionBusiness))
    }
  }

  @Test
  fun `should convert session database to business`() = responseScenario<Session> {
    whenAction {
      sessionDetailDatabase.asBusinessSession()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(sessionBusiness)
    }
  }

  @Test
  fun `should convert session business to database`() = responseScenario<SessionRoomEntity> {
    whenAction {
      sessionBusiness.asDatabaseSession()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(sessionDatabase)
    }
  }

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

  @Test
  fun `should convert flip database list to business list`() =
    responseScenario<List<SessionTournamentFlip>> {
      whenAction {
        listOf(sessionFlipDatabase).asBusinessTournamentFlips()
      }

      thenAssert { result ->
        assertThat(result).isEqualTo(listOf(sessionFlipBusiness))
      }
    }

  @Test
  fun `should convert flip database to business`() = responseScenario<SessionTournamentFlip> {
    whenAction {
      sessionFlipDatabase.asBusinessTournamentFlips()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(sessionFlipBusiness)
    }
  }

  @Test
  fun `should convert flip business to database`() =
    responseScenario<SessionTournamentFlipRoomEntity> {
      whenAction {
        sessionFlipBusiness.asBusinessTournamentFlips()
      }

      thenAssert { result ->
        assertThat(result).isEqualTo(sessionFlipDatabase)
      }
    }
}