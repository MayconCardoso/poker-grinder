package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.CalendarTestRule
import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.common_test.UuidTestRule
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.WithdrawUseCase
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newTournament
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class RegisterTournamentUseCaseTest {

  @get:Rule
  val calendarRule = CalendarTestRule()

  @get:Rule
  val uuidRule = UuidTestRule()

  private val repository = mockk<GrindRepository>(relaxed = true)
  private val withdrawUseCase = mockk<WithdrawUseCase>(relaxed = true)
  private val generateUniqueIdUseCase = mockk<GenerateUniqueIdUseCase>(relaxed = true)

  private val target = RegisterTournamentUseCase(
    repository = repository,
    withdrawUseCase = withdrawUseCase,
    generateUniqueIdUseCase = generateUniqueIdUseCase,
  )

  @Test
  fun `should register tournament`() = simpleScenario {
    val session = newSession(id = "10")
    val transactionId = "50"
    val tournamentBuyIn = 11.00
    val tournamentTitle = "Bounty Builder $11"
    val expectedTournament = newTournament(
      id = "1000",
      buyIn = tournamentBuyIn,
      title = tournamentTitle,
      idSession = session.id,
      idTransactionBuyIn = transactionId,
      idTransactionProfit = null,
      startTimeInMs = 20000
    )

    givenScenario {
      coEvery { generateUniqueIdUseCase.invoke() } returns "1000"
      coEvery { calendarRule.calendar.timeInMillis } returns 20000
      coEvery { withdrawUseCase.invoke(any(), any(), any()) } returns transactionId
    }

    whenAction {
      target(session = session, title = tournamentTitle, buyIn = tournamentBuyIn)
    }

    thenAssert {
      // Capture slot.
      val sessionSlot = slot<SessionTournament>()

      // Check calling chain.
      coVerifyOrder {
        withdrawUseCase(tournamentBuyIn, tournamentTitle, BankrollTransactionType.BUY_IN)
        generateUniqueIdUseCase()
        repository.saveGrindTournament(capture(sessionSlot))
      }

      // Confirm calling chain
      confirmVerified(withdrawUseCase, generateUniqueIdUseCase, repository)

      // Assert saved data.
      assertThat(sessionSlot.captured).isEqualTo(expectedTournament)
    }
  }
}