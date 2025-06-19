package com.mctech.pokergrinder.grind_tournament.data

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.grind_tournament.data.database.GrindTournamentDao
import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.grind_tournament.data.mapper.asBusinessTournaments
import com.mctech.pokergrinder.grind_tournament.data.mapper.asDatabaseTournaments
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.testing.newTournament
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GrindTournamentRepositoryImplTest {

  private val dao = mockk<GrindTournamentDao>(relaxed = true)
  private val target = GrindTournamentRepositoryImpl(
    grindDao = dao,
  )

  @Test
  fun `should delegate observe all grind tournaments`() =
    responseScenario<Flow<List<SessionTournament>>> {
      val items = listOf(
        newDatabaseTournament(id = "1"),
        newDatabaseTournament(id = "2"),
      )
      val flow = flow {
        emit(items)
      }

      givenScenario {
        every { dao.observeGrindTournaments(any()) } returns flow
      }

      whenAction {
        target.observeGrindTournament("10")
      }

      thenAssert { result ->
        assertThat(result.last()).isEqualTo(items.asBusinessTournaments())
        coVerifyOrder { dao.observeGrindTournaments("10") }
        confirmVerified(dao)
      }
    }

  @Test
  fun `should delegate save session tournament flow`() = simpleScenario {
    val session = newTournament(id = "1")

    whenAction {
      target.saveGrindTournament(session)
    }

    thenAssert {
      val sessionSlot = slot<SessionTournamentRoomEntity>()
      coVerify { dao.save(capture(sessionSlot)) }
      assertThat(sessionSlot.captured).isEqualTo(session.asDatabaseTournaments())

      coVerifyOrder { dao.save(any()) }
      confirmVerified(dao)
    }
  }

}