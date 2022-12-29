package com.mctech.pokergrinder.tournament.data

import com.mctech.pokergrinder.threading.CoroutineDispatchers
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import com.mctech.pokergrinder.tournament.data.mapper.asBusinessTournament
import com.mctech.pokergrinder.tournament.data.mapper.asBusinessTournaments
import com.mctech.pokergrinder.tournament.data.mapper.asDatabaseTournament
import com.mctech.pokergrinder.tournament.domain.TournamentRepository
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TournamentRepositoryImpl @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val tournamentDao: TournamentDao,
) : TournamentRepository {

  override fun observe(): Flow<List<Tournament>> {
    return tournamentDao.observe().map { it.asBusinessTournaments() }
  }

  override suspend fun save(tournament: Tournament): Unit = withContext(dispatchers.io) {
    tournamentDao.save(tournament.asDatabaseTournament())
  }

  override suspend fun load(title: String): Tournament? = withContext(dispatchers.io) {
    tournamentDao.loadByTitle(title)?.asBusinessTournament()
  }
}