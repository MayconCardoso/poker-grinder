package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import javax.inject.Inject

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