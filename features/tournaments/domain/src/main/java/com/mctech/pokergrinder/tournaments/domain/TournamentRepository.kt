package com.mctech.pokergrinder.tournaments.domain

import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.flow.Flow

/**
 * Tournament repository to access tournament data.
 */
interface TournamentRepository {

  /**
   * Observes saved tournaments.
   */
  fun observe(): Flow<List<Tournament>>

  /**
   * Used to save a [tournament].
   */
  suspend fun save(tournament: Tournament)
}