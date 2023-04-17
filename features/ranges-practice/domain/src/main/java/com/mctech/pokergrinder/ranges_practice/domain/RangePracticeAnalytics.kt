package com.mctech.pokergrinder.ranges_practice.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import javax.inject.Inject

/**
 * Used to send analytics for the feature.
 */
class RangePracticeAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender,
) {
  suspend fun onStartPracticeClicked() {
    analyticsSender.send(event = AnalyticsEvent(name = "range_practice_screen_start_clicked"))
  }

  suspend fun onQuestionGenerated() {
    analyticsSender.send(event = AnalyticsEvent(name = "range_practice_question_generated"))
  }

  suspend fun onActionPerformed(action: RangeAction) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "range_practice_question_answered",
        data = listOf(
          "action" to action.name
        )
      )
    )
  }

  suspend fun onSeeRangeClicked() {
    analyticsSender.send(event = AnalyticsEvent(name = "range_practice_range_viewed"))
  }

  suspend fun onFilterClicked() {
    analyticsSender.send(event = AnalyticsEvent(name = "range_practice_filter_viewed"))
  }

  suspend fun onFilterChanged() {
    analyticsSender.send(event = AnalyticsEvent(name = "range_practice_filter_changed"))
  }

}