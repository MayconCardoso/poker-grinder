package com.mctech.pokergrinder.grind_gameplay.data

import com.mctech.pokergrinder.grind_gameplay.data.database.GrindGameplayDao
import com.mctech.pokergrinder.grind_gameplay.data.mapper.asBusinessTournamentFlips
import com.mctech.pokergrinder.grind_gameplay.domain.GrindGameplayRepository
import com.mctech.pokergrinder.grind_gameplay.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Grind repository implementation.
 *
 * @property grindDao used to persist all grind related data.
 */
class GrindGameplayRepositoryImpl @Inject constructor(
  private val grindDao: GrindGameplayDao,
) : GrindGameplayRepository {

  override fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlip>> {
    return grindDao.observeGrindTournamentFlips(sessionId).map { it.asBusinessTournamentFlips() }
  }

  override suspend fun saveGrindTournamentFlip(flip: SessionTournamentFlip) {
    grindDao.save(flip.asBusinessTournamentFlips())
  }

}