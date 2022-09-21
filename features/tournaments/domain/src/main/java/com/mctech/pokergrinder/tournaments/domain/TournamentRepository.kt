package com.mctech.pokergrinder.tournaments.domain

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow

interface TournamentRepository {
  fun observe(): Flow<List<Tournament>>

  suspend fun save(tournament: Tournament)

  suspend fun delete(tournament: Tournament)
}