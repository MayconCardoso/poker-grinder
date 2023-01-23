package com.mctech.pokergrinder.grind_gameplay.data.mapper

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind_gameplay.data.newDatabaseSessionFlip
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind_gameplay.testing.newSessionFlip
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindMapperKtTest {

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