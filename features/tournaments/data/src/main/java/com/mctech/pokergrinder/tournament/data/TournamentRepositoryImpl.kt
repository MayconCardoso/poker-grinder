package com.mctech.pokergrinder.tournament.data

import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import com.mctech.pokergrinder.tournament.data.mapper.asBusinessTournaments
import com.mctech.pokergrinder.tournament.data.mapper.asDatabaseTournament
import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

public class TournamentRepositoryImpl @Inject constructor(
  private val tournamentDao: TournamentDao,
) : TournamentRepository {
  override fun observe(): Flow<List<Tournament>> {
    return tournamentDao.observe().map { it.asBusinessTournaments() }
  }

  override suspend fun save(tournament: Tournament) {
    tournamentDao.save(tournament.asDatabaseTournament())
  }

  override suspend fun delete(tournament: Tournament) {
    tournamentDao.save(tournament.asDatabaseTournament())
  }
}