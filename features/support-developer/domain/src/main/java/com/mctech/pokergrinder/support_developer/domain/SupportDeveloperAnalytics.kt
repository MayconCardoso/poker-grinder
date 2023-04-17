package com.mctech.pokergrinder.support_developer.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import javax.inject.Inject

class SupportDeveloperAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender
) {
  suspend fun onSupportDeveloperViewed() {
    analyticsSender.send(event = AnalyticsEvent(name = "support_developer_screen_viewed"))
  }
}