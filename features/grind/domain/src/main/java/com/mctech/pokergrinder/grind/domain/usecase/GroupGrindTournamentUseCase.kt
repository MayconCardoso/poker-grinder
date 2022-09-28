package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import javax.inject.Inject
import kotlin.math.min

class GroupGrindTournamentUseCase @Inject constructor() {

  operator fun invoke(tournaments: List<SessionTournament>) = tournaments
    .groupBy { it.title }
    .values
    .map {
      it.reduce { acc, stockShare ->
        SessionTournament(
          id = "",
          idSession = acc.idSession,
          idTransactionProfit = null,
          idTransactionBuyIn = "",
          title = acc.title,
          buyIn = acc.buyIn + stockShare.buyIn,
          profit = acc.profit + stockShare.profit,
          isGrouped = true,
          startTimeInMs = min(acc.startTimeInMs, stockShare.startTimeInMs)
        )
      }
    }
    .sortedByDescending { it.startTimeInMs }

}