package com.mctech.pokergrinder.tournaments.domain.usecase

import com.mctech.pokergrind.threading.CoroutineDispatchers
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import javax.inject.Inject

class ComputesInvestmentPerSessionUseCase @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
) {
  suspend operator fun invoke(tournaments: List<Tournament>) = withContext(dispatchers.default) {
    // Format average.
    if (tournaments.isNotEmpty()) {
      DecimalFormat("$#0.00").format(tournaments.map { it.buyIn }.sum())
    }
    // Returns regular default string.
    else {
      "-"
    }
  }
}