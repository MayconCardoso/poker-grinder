package com.mctech.pokergrinder.grind.domain

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access grind data.
 */
interface GrindRepository {

  /**
   * Observes current grind session.
   */
  fun observeCurrentGrind(): Flow<Session?>

  /**
   * Observes all grind sessions.
   */
  fun observeAllGrinds(): Flow<List<Session>>

  /**
   * Observes grind given its [sessionId].
   */
  fun observeGrind(sessionId: String): Flow<Session>

  /**
   * Observes grind played flips given its [sessionId].
   */
  fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlip>>

  /**
   * Saves a grind session.
   */
  suspend fun saveGrind(session: Session)

  /**
   * Saves a grind flip
   */
  suspend fun saveGrindTournamentFlip(flip: SessionTournamentFlip)

  /**
   * Loads current session
   */
  suspend fun loadCurrentSession(): Session?
}