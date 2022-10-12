package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import javax.inject.Inject

class RegisterTournamentFlipUseCase @Inject constructor(
  private val repository: GrindRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {
  suspend operator fun invoke(
    session: Session,
    title: String,
    heroCards: List<Card>,
    boardCards: List<Card>,
    villainCards: List<Card>,
    heroWon: Boolean,
  ) {
    // Creates tournament
    val flip = SessionTournamentFlip(
      id = generateUniqueIdUseCase(),
      idSession = session.id,
      tournament = title,
      board = boardCards.joinToString(separator = "-") { it.formattedName() },
      heroHand = heroCards.joinToString(separator = "-") { it.formattedName() },
      villainHand = villainCards.joinToString(separator = "-") { it.formattedName() },
      won = heroWon,
    )

    // Saves updated session.
    repository.saveGrindTournamentFlip(flip)
  }
}