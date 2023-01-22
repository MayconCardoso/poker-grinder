package com.mctech.pokergrinder.grind_tournament.domain

import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access grind tournament data.
 */
interface GrindTournamentRepository {

  /**
   * Observes grind played tournaments given its [sessionId].
   */
  fun observeGrindTournament(sessionId: String): Flow<List<SessionTournament>>

  /**
   * Saves a grind tournament
   */
  suspend fun saveGrindTournament(sessionTournament: SessionTournament)

}