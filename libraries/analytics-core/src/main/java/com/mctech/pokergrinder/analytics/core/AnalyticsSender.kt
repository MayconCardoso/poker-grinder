package com.mctech.pokergrinder.analytics.core

/**
 * Analytics interface used to send events.
 */
interface AnalyticsSender {

  /**
   * Called to send a new event.
   */
  suspend fun send(event: AnalyticsEvent)
}