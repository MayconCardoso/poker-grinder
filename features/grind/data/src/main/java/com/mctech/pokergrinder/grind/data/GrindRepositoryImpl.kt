package com.mctech.pokergrinder.grind.data

import com.mctech.pokergrind.threading.CoroutineDispatchers
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class GrindRepositoryImpl @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
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

  override suspend fun loadCurrentSession(): Session? = withContext(dispatchers.io) {
    grindDao.loadCurrentGrind()?.asBusinessSession()
  }

  override suspend fun loadGrind(id: String): Session = withContext(dispatchers.io) {
    grindDao.loadGrind(id).asBusinessSession()
  }

  override suspend fun loadGrindTournament(
    id: String,
  ): SessionTournament = withContext(dispatchers.io) {
    grindDao.loadGrindTournament(id).asBusinessTournaments()
  }
}