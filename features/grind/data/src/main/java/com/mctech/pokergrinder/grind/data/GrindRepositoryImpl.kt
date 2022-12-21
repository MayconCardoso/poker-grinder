package com.mctech.pokergrinder.grind.data

import com.mctech.pokergrinder.threading.CoroutineDispatchers
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSession
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSessions
import com.mctech.pokergrinder.grind.data.mapper.asBusinessTournamentFlips
import com.mctech.pokergrinder.grind.data.mapper.asBusinessTournaments
import com.mctech.pokergrinder.grind.data.mapper.asDatabaseSession
import com.mctech.pokergrinder.grind.data.mapper.asDatabaseTournaments
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Grind repository implementation.
 *
 * @property dispatchers used to handle coroutine threads.
 * @property grindDao used to persist all grind related data.
 */
public class GrindRepositoryImpl @Inject constructor(
  private val grindDao: GrindDao,
) : GrindRepository {

  override fun observeAllGrinds(): Flow<List<Session>> {
    return grindDao.observeAllGrind().map { it.asBusinessSessions() }
  }

  override fun observeCurrentGrind(): Flow<Session?> {
    return grindDao.observeCurrentGrind().map { it?.asBusinessSession() }
  }

  override fun observeGrind(sessionId: String): Flow<Session> {
    return grindDao.observeGrind(sessionId).map { it.asBusinessSession() }
  }

  override fun observeGrindTournament(sessionId: String): Flow<List<SessionTournament>> {
    return grindDao.observeGrindTournaments(sessionId).map { it.asBusinessTournaments() }
  }

  override fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlip>> {
    return grindDao.observeGrindTournamentFlips(sessionId).map { it.asBusinessTournamentFlips() }
  }

  override suspend fun saveGrind(session: Session) {
    grindDao.save(session.asDatabaseSession())
  }

  override suspend fun saveGrindTournament(sessionTournament: SessionTournament) {
    grindDao.saveTournament(sessionTournament.asDatabaseTournaments())
  }

  override suspend fun saveGrindTournamentFlip(flip: SessionTournamentFlip) {
    grindDao.saveTournamentFlip(flip.asBusinessTournamentFlips())
  }

  override suspend fun loadCurrentSession(): Session? {
    return grindDao.loadCurrentGrind()?.asBusinessSession()
  }
}