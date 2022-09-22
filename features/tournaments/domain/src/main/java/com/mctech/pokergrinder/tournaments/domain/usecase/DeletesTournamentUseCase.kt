package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import javax.inject.Inject

class DeletesTournamentUseCase @Inject constructor(
  private val repository: TournamentRepository,
){
  suspend operator fun invoke(tournament: Tournament) {
    repository.delete(tournament)
  }
}