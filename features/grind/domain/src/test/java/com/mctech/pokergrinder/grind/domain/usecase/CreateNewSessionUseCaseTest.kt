package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.CalendarTestRule
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.testing.newSession
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class CreateNewSessionUseCaseTest {

  @get:Rule
  val calendarRule = CalendarTestRule()

  private val repository = mockk<GrindRepository>(relaxed = true)
  private val generateUniqueIdUseCase = mockk<GenerateUniqueIdUseCase>(relaxed = true)

  private val target = CreateNewSessionUseCase(
    repository = repository,
    generateUniqueIdUseCase = generateUniqueIdUseCase,
  )

  @Test
  fun `should create a new session`() = simpleScenario {
    givenScenario {
      coEvery { generateUniqueIdUseCase() } returns "10"
      coEvery { repository.loadCurrentSession() } returns null
      coEvery { calendarRule.calendar.timeInMillis } returns 0
    }

    whenAction {
      target(title = "This is my session")
    }

    thenAssert {
      // Creates spy
      val sessionSlotSpy = slot<Session>()

      // Checks calling pipeline
      coVerifyOrder {
        repository.loadCurrentSession()
        generateUniqueIdUseCase()
        repository.saveGrind(capture(sessionSlotSpy))
      }

      // Confirm no more calls.
      confirmVerified(repository, generateUniqueIdUseCase)

      // Assert saved transaction
      assertThat(sessionSlotSpy.captured).isEqualTo(
        newSession(id = "10", title = "This is my session", isOpened = true)
      )
    }
  }

  @Test
  fun `should close existent session`() = simpleScenario {
    givenScenario {
      coEvery { generateUniqueIdUseCase() } returns "10"
      coEvery { repository.loadCurrentSession() } returns newSession(id= "20", isOpened = true)
      coEvery { calendarRule.calendar.timeInMillis } returns 0
    }

    whenAction {
      target(title = "This is my session")
    }

    thenAssert {
      // Creates spy
      val sessionSlotSpy = slot<Session>()
      val currentSessionSlotSpy = slot<Session>()

      // Checks calling pipeline
      coVerifyOrder {
        repository.loadCurrentSession()
        repository.saveGrind(capture(currentSessionSlotSpy))
        generateUniqueIdUseCase()
        repository.saveGrind(capture(sessionSlotSpy))
      }

      // Confirm no more calls.
      confirmVerified(repository, generateUniqueIdUseCase)

      // Assert open session save transaction
      assertThat(currentSessionSlotSpy.captured).isEqualTo(
        newSession(id = "20", isOpened = false)
      )

      // Assert saved transaction
      assertThat(sessionSlotSpy.captured).isEqualTo(
        newSession(id = "10", title = "This is my session", isOpened = true)
      )
    }
  }
}
