package com.mctech.pokergrinder.tournament.domain.usecase

import com.mctech.pokergrinder.tournament.domain.TournamentRepository
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import javax.inject.Inject

/**
 * Responsible for saving a [Tournament].
 *
 * @property repository tournament data repository.
 * @property generateUniqueIdUseCase use case responsible for generating a unique id.
 */
class SavesTournamentUseCase @Inject constructor(
  private val repository: TournamentRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
){
  suspend operator fun invoke(tournament: Tournament) {
    // Resolves tournament
    val newTournament = if(tournament.id.isBlank()) {
      tournament.copy(id = generateUniqueIdUseCase())
    }
    else {
      tournament
    }

    // Saves it
    repository.save(newTournament)
  }
}