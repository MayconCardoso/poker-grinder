package com.mctech.pokergrinder.grind.domain

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow

interface GrindRepository {
  fun observeCurrentGrind(): Flow<Session?>

  fun observeAllGrinds(): Flow<List<Session>>

  fun observeGrind(sessionId: String): Flow<Session>

  fun observeGrindTournament(sessionId: String): Flow<List<SessionTournament>>

  fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlip>>

  suspend fun saveGrind(session: Session)

  suspend fun saveGrindTournament(sessionTournament: SessionTournament)

  suspend fun saveGrindTournamentFlip(flip: SessionTournamentFlip)

  suspend fun loadCurrentSession(): Session?

  suspend fun loadGrind(id: String): Session

  suspend fun loadGrindTournament(id: String): SessionTournament
}