package com.mctech.pokergrinder.ranges.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import javax.inject.Inject

/**
 * Used to send analytics for the feature.
 */
class RangeAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender,
) {

  suspend fun onRangeViewed(range: Range) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "ranges_screen_range_clicked",
        data = listOf(
          "range" to range.name
        )
      )
    )
  }

}