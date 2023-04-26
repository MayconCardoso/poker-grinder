package com.mctech.pokergrinder.analytics.clients

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.mctech.pokergrinder.analytics.core.AnalyticsEvent

class LogcatClient : AnalyticsClient {

  override suspend fun sendEvent(event: AnalyticsEvent) {
    logEvent(
      event.name,
      Bundle().apply {
        event.data.forEach {
          putString(it.first, it.second.toString())
        }
      }
    )
  }

  private fun logEvent(name: String, data: Bundle) {
    Log.i("PokerAnalytics", "$name -> $data")
  }

}