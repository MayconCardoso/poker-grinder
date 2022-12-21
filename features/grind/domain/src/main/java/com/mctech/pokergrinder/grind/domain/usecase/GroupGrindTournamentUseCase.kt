package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import javax.inject.Inject
import kotlin.math.min

/**
 * Used to group same tournament statistics.
 */
class GroupGrindTournamentUseCase @Inject constructor() {

  operator fun invoke(tournaments: List<SessionTournament>) = tournaments
    .groupBy { it.title }
    .values
    .map {
      it.reduce { acc, reduced ->
        SessionTournament(
          id = "",
          idSession = acc.idSession,
          idTransactionProfit = null,
          idTransactionBuyIn = "",
          title = acc.title,
          buyIn = acc.buyIn + reduced.buyIn,
          profit = acc.profit + reduced.profit,
          isGrouped = true,
          startTimeInMs = min(acc.startTimeInMs, reduced.startTimeInMs)
        )
      }
    }
    .sortedByDescending { it.startTimeInMs }

}