package com.mctech.pokergrinder.analytics.clients

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent

/**
 * Defines a analytics client.
 */
interface AnalyticsClient {

  /**
   * Called to send a new event.
   */
  suspend fun sendEvent(event: AnalyticsEvent)
}