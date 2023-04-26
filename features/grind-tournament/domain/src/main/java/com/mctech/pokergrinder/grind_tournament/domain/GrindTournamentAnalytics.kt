package com.mctech.pokergrinder.grind_tournament.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import javax.inject.Inject

/**
 * Used to send analytics for the feature.
 */
class GrindTournamentAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender,
) {

  suspend fun onNewTourneyClicked() {
    analyticsSender.send(event = AnalyticsEvent(name = "session_screen_new_tourney_clicked"))
  }

  suspend fun onTourneyClicked(tournament: SessionTournament) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "session_screen_tourney_clicked",
        data = listOf(
          "title" to tournament.title,
          "buy_in" to tournament.buyIn,
        )
      )
    )
  }

  suspend fun onTourneyDuplicated(tournament: SessionTournament) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "session_screen_tourney_duplicated",
        data = listOf(
          "title" to tournament.title,
          "buy_in" to tournament.buyIn,
        )
      )
    )
  }

  suspend fun onTourneyRegistered(title: String, buyIn: Double) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "session_screen_tourney_registered",
        data = listOf(
          "title" to title,
          "buy_in" to buyIn,
        )
      )
    )
  }

  suspend fun onTourneyUpdated(title: String, buyIn: Double, profit: Double) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "session_screen_tourney_updated",
        data = listOf(
          "title" to title,
          "buy_in" to buyIn,
          "profit" to profit,
        )
      )
    )
  }

}