package com.mctech.pokergrinder.grind_gameplay.domain

import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access grind gameplay data.
 */
interface GrindGameplayRepository {

  /**
   * Observes grind played flips given its [sessionId].
   */
  fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlip>>

  /**
   * Saves a grind flip
   */
  suspend fun saveGrindTournamentFlip(flip: SessionTournamentFlip)

}