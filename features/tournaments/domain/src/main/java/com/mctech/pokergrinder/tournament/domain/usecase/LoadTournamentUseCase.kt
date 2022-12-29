package com.mctech.pokergrinder.tournament.domain.usecase

import com.mctech.pokergrinder.tournament.domain.TournamentRepository
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import javax.inject.Inject

/**
 * Load a tournament by its title [Tournament].
 *
 * @property repository tournament data repository.
 */
class LoadTournamentUseCase @Inject constructor(
  private val repository: TournamentRepository,
){
  suspend operator fun invoke(title: String): Tournament? {
    return repository.load(title.trim())
  }
}