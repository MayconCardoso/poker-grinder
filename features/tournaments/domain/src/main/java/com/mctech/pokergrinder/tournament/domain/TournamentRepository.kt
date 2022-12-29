package com.mctech.pokergrinder.tournament.domain

import com.mctech.pokergrinder.tournament.domain.entities.Tournament
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

  /**
   * Load tournament by its title
   */
  suspend fun load(title: String): Tournament?
}