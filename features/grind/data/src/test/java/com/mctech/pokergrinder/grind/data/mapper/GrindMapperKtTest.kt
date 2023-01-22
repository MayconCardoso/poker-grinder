package com.mctech.pokergrinder.grind.data.mapper

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.newDatabaseSession
import com.mctech.pokergrinder.grind.data.newDatabaseSessionDetail
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.testing.newSession
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
}