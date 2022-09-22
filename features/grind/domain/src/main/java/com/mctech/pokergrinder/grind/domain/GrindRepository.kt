package com.mctech.pokergrinder.grind.domain

import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import kotlinx.coroutines.flow.Flow

interface GrindRepository {
  fun observeCurrentGrind(): Flow<Session?>

  fun observeAllGrinds(): Flow<List<Session>>

  fun observeGrind(sessionId: String): Flow<Session>

  fun observeGrindTournament(sessionId: String): Flow<List<SessionTournament>>

  suspend fun saveGrind(session: Session)

  suspend fun saveGrindTournament(sessionTournament: SessionTournament)

  suspend fun loadCurrentSession(): Session?
}