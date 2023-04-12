package com.mctech.pokergrinder.grind_tournament.domain.usecase

import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import java.lang.Long.max
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
          startTimeInMs = max(acc.startTimeInMs, reduced.startTimeInMs)
        )
      }
    }
    .sortedByDescending { it.startTimeInMs }

}