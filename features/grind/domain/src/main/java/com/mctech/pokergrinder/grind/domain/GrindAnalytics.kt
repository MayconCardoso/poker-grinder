package com.mctech.pokergrinder.grind.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.grind.domain.entities.Session
import javax.inject.Inject

/**
 * Used to send analytics for the feature.
 */
class GrindAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender,
) {

  suspend fun onCreateSessionClicked() {
    analyticsSender.send(event = AnalyticsEvent(name = "sessions_screen_create_session_clicked"))
  }

  suspend fun onSessionViewed(session: Session) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "sessions_screen_session_clicked",
        data = listOf(
          "title" to session.title
        )
      )
    )
  }

  suspend fun onSessionCreated(title: String) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "sessions_screen_session_created",
        data = listOf(
          "title" to title
        )
      )
    )
  }

}