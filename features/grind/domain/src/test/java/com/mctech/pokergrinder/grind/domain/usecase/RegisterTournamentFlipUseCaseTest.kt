package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.CardSuit
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import com.mctech.pokergrinder.grind.testing.newSession
import com.mctech.pokergrinder.grind.testing.newSessionFlip
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class RegisterTournamentFlipUseCaseTest {
  private val repository = mockk<GrindRepository>(relaxed = true)
  private val generateUniqueIdUseCase = mockk<GenerateUniqueIdUseCase>(relaxed = true)
  private val session = newSession(id = "10")
  private val tournamentTitle = "Bounty Builder $11"

  private val target = RegisterTournamentFlipUseCase(
    repository = repository,
    generateUniqueIdUseCase = generateUniqueIdUseCase,
  )

  @Test
  fun `should save hero winner hand`() = simpleScenario {
    val expectedFlip = newSessionFlip(
      id = "100",
      idSession = "10",
      tournament = tournamentTitle,
      heroHand = "ac-ad",
      villainHand = "kc-kd",
      board = "kh-as-2s-3h-9s",
      won = true,
    )

    givenScenario {
      coEvery { generateUniqueIdUseCase() } returns "100"
    }

    whenAction {
      target(
        session = session,
        title = tournamentTitle,
        heroCards = listOf(
          Card(suit = CardSuit.CLUBS, name = "A", value = 14),
          Card(suit = CardSuit.DIAMONDS, name = "A", value = 14),
        ),
        villainCards = listOf(
          Card(suit = CardSuit.CLUBS, name = "K", value = 13),
          Card(suit = CardSuit.DIAMONDS, name = "K", value = 13),
        ),
        boardCards = listOf(
          Card(suit = CardSuit.HEARTS, name = "K", value = 13),
          Card(suit = CardSuit.SPADES, name = "A", value = 14),
          Card(suit = CardSuit.SPADES, name = "2", value = 2),
          Card(suit = CardSuit.HEARTS, name = "3", value = 3),
          Card(suit = CardSuit.SPADES, name = "9", value = 8),
        ),
        heroWon = true,
      )
    }

    thenAssert {
      // Get slot
      val flipSlot = slot<SessionTournamentFlip>()

      // Check calling chain.
      coVerifyOrder {
        repository.saveGrindTournamentFlip(capture(flipSlot))
      }

      // Confirm all calls.
      confirmVerified(repository)

      // Checks result
      assertThat(flipSlot.captured).isEqualTo(expectedFlip)
    }
  }

  @Test
  fun `should save hero loser hand`() = simpleScenario {
    val expectedFlip = newSessionFlip(
      id = "200",
      idSession = "10",
      tournament = tournamentTitle,
      heroHand = "2c-3c",
      villainHand = "4c-5c",
      board = "ac-kc-jc-3h-9s",
      won = false,
    )

    givenScenario {
      coEvery { generateUniqueIdUseCase() } returns "200"
    }

    whenAction {
      target(
        session = session,
        title = tournamentTitle,
        heroCards = listOf(
          Card(suit = CardSuit.CLUBS, name = "2", value = 2),
          Card(suit = CardSuit.CLUBS, name = "3", value = 3),
        ),
        villainCards = listOf(
          Card(suit = CardSuit.CLUBS, name = "4", value = 4),
          Card(suit = CardSuit.CLUBS, name = "5", value = 5),
        ),
        boardCards = listOf(
          Card(suit = CardSuit.CLUBS, name = "A", value = 14),
          Card(suit = CardSuit.CLUBS, name = "K", value = 13),
          Card(suit = CardSuit.CLUBS, name = "J", value = 11),
          Card(suit = CardSuit.HEARTS, name = "3", value = 3),
          Card(suit = CardSuit.SPADES, name = "9", value = 8),
        ),
        heroWon = false,
      )
    }

    thenAssert {
      // Get slot
      val flipSlot = slot<SessionTournamentFlip>()

      // Check calling chain.
      coVerifyOrder {
        repository.saveGrindTournamentFlip(capture(flipSlot))
      }

      // Confirm all calls.
      confirmVerified(repository)

      // Checks result
      assertThat(flipSlot.captured).isEqualTo(expectedFlip)
    }
  }
}