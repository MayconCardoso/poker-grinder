package com.mctech.pokergrinder.grind_gameplay.data

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.grind_gameplay.data.database.GrindGameplayDao
import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind_gameplay.data.mapper.asBusinessTournamentFlips
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind_gameplay.testing.newSessionFlip
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

internal class GrindRepositoryImplTest {

  private val dao = mockk<GrindGameplayDao>(relaxed = true)
  private val target = GrindGameplayRepositoryImpl(
    grindDao = dao,
  )

  @Test
  fun `should delegate observe all grind flips`() =
    responseScenario<Flow<List<SessionTournamentFlip>>> {
      val items = listOf(
        newDatabaseSessionFlip(id = "1"),
        newDatabaseSessionFlip(id = "2"),
      )
      val flow = flow {
        emit(items)
      }

      givenScenario {
        every { dao.observeGrindTournamentFlips(any()) } returns flow
      }

      whenAction {
        target.observeGrindTournamentFlips("10")
      }

      thenAssert { result ->
        assertThat(result.last()).isEqualTo(items.asBusinessTournamentFlips())
        coVerifyOrder { dao.observeGrindTournamentFlips("10") }
        confirmVerified(dao)
      }
    }

  @Test
  fun `should delegate save session flip flow`() = simpleScenario {
    val session = newSessionFlip(id = "1")

    whenAction {
      target.saveGrindTournamentFlip(session)
    }

    thenAssert {
      val sessionSlot = slot<SessionTournamentFlipRoomEntity>()
      coVerify { dao.save(capture(sessionSlot)) }
      assertThat(sessionSlot.captured).isEqualTo(session.asBusinessTournamentFlips())

      coVerifyOrder { dao.save(any()) }
      confirmVerified(dao)
    }
  }
}