package com.mctech.pokergrinder.grind.data

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSession
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSessions
import com.mctech.pokergrinder.grind.data.mapper.asDatabaseSession
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.testing.newSession
import io.mockk.coEvery
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

  private val dao = mockk<GrindDao>(relaxed = true)
  private val target = GrindRepositoryImpl(
    grindDao = dao,
  )

  @Test
  fun `should delegate observe all grind sessions`() = responseScenario<Flow<List<Session>>> {
    val items = listOf(
      newDatabaseSessionDetail(id = "1"),
      newDatabaseSessionDetail(id = "2"),
    )
    val flow = flow {
      emit(items)
    }

    givenScenario {
      every { dao.observeAllGrind() } returns flow
    }

    whenAction {
      target.observeAllGrinds()
    }

    thenAssert { result ->
      assertThat(result.last()).isEqualTo(items.asBusinessSessions())
      coVerifyOrder { dao.observeAllGrind() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate observe current grind`() = responseScenario<Flow<Session?>> {
    val flow = flow {
      emit(newDatabaseSessionDetail(id = "1"))
    }

    givenScenario {
      every { dao.observeCurrentGrind() } returns flow
    }

    whenAction {
      target.observeCurrentGrind()
    }

    thenAssert { result ->
      assertThat(result.last()).isEqualTo(newDatabaseSessionDetail(id = "1").asBusinessSession())
      coVerifyOrder { dao.observeCurrentGrind() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate load current grind`() = responseScenario<Session?> {
    givenScenario {
      coEvery { dao.loadCurrentGrind() } returns newDatabaseSessionDetail(id = "1")
    }

    whenAction {
      target.loadCurrentSession()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(newDatabaseSessionDetail(id = "1").asBusinessSession())
      coVerifyOrder { dao.loadCurrentGrind() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate load current grind when it is null`() = responseScenario<Session?> {
    givenScenario {
      coEvery { dao.loadCurrentGrind() } returns null
    }

    whenAction {
      target.loadCurrentSession()
    }

    thenAssert { result ->
      assertThat(result).isNull()
      coVerifyOrder { dao.loadCurrentGrind() }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate observe grind by id`() = responseScenario<Flow<Session>> {
    val flow = flow {
      emit(newDatabaseSessionDetail(id = "1"))
    }

    givenScenario {
      every { dao.observeGrind(any()) } returns flow
    }

    whenAction {
      target.observeGrind("1")
    }

    thenAssert { result ->
      assertThat(result.last()).isEqualTo(newDatabaseSessionDetail(id = "1").asBusinessSession())
      coVerifyOrder { dao.observeGrind("1") }
      confirmVerified(dao)
    }
  }

  @Test
  fun `should delegate save session flow`() = simpleScenario {
    val session = newSession(id = "1")

    whenAction {
      target.saveGrind(session)
    }

    thenAssert {
      val sessionSlot = slot<SessionRoomEntity>()
      coVerify { dao.save(capture(sessionSlot)) }
      assertThat(sessionSlot.captured).isEqualTo(session.asDatabaseSession())

      coVerifyOrder { dao.save(any()) }
      confirmVerified(dao)
    }
  }

}