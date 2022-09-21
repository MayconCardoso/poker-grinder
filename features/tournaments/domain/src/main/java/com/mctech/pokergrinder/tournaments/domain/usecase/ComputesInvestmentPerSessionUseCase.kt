package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrind.threading.CoroutineDispatchers
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComputesInvestmentPerSessionUseCase @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
) {
  suspend operator fun invoke(tournaments: List<Tournament>) = withContext(dispatchers.default) {
    if (tournaments.isEmpty()) {
      "-"
    } else {
      tournaments.map { it.buyIn }.sum().toString()
    }
  }
}