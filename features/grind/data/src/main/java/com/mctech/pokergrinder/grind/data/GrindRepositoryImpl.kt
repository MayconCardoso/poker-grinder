package com.mctech.pokergrinder.grind.data

import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSession
import com.mctech.pokergrinder.grind.data.mapper.asBusinessSessions
import com.mctech.pokergrinder.grind.data.mapper.asDatabaseSession
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Grind repository implementation.
 *
 * @property dispatchers used to handle coroutine threads.
 * @property grindDao used to persist all grind related data.
 */
class GrindRepositoryImpl @Inject constructor(
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

  override suspend fun saveGrind(session: Session) {
    grindDao.save(session.asDatabaseSession())
  }

  override suspend fun loadCurrentSession(): Session? {
    return grindDao.loadCurrentGrind()?.asBusinessSession()
  }
}