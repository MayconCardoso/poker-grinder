package com.mctech.pokergrinder.analytics.sender

import com.mctech.pokergrinder.analytics.BuildConfig
import com.mctech.pokergrinder.analytics.clients.FirebaseClient
import com.mctech.pokergrinder.analytics.clients.LogcatClient
import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class AnalyticsSenderImpl @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
) : AnalyticsSender {

  /**
   * Hold all analytics clients the data is sent to.
   */
  private val clients = buildList {
    if (BuildConfig.DEBUG) {
      add(LogcatClient())
    }
    add(FirebaseClient())
  }

  /**
   * Sends an event to all analytics client.
   */
  override suspend fun send(event: AnalyticsEvent) = withContext(dispatchers.io) {
    clients.forEach { client ->
      client.sendEvent(event)
    }
  }

}