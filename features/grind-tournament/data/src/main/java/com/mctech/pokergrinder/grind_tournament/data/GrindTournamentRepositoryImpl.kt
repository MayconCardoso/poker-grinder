package com.mctech.pokergrinder.grind_tournament.data

import com.mctech.pokergrinder.grind_tournament.data.database.GrindTournamentDao
import com.mctech.pokergrinder.grind_tournament.data.mapper.asBusinessTournaments
import com.mctech.pokergrinder.grind_tournament.data.mapper.asDatabaseTournaments
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentRepository
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Grind tournament repository implementation.
 *
 * @property grindDao used to persist all grind related data.
 */
class GrindTournamentRepositoryImpl @Inject constructor(
  private val grindDao: GrindTournamentDao,
) : GrindTournamentRepository {

  override fun observeGrindTournament(sessionId: String): Flow<List<SessionTournament>> {
    return grindDao.observeGrindTournaments(sessionId).map { it.asBusinessTournaments() }
  }

  override suspend fun saveGrindTournament(sessionTournament: SessionTournament) {
    grindDao.saveTournament(sessionTournament.asDatabaseTournaments())
  }

}