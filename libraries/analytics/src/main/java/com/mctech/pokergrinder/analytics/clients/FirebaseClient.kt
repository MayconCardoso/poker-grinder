package com.mctech.pokergrinder.analytics.clients

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.mctech.pokergrinder.analytics.core.AnalyticsEvent

class FirebaseClient : AnalyticsClient {

  private val analytics by lazy { Firebase.analytics }

  override suspend fun sendEvent(event: AnalyticsEvent) {
    analytics.logEvent(
      event.name,
      Bundle().apply {
        event.data.forEach {
          putString(it.first, it.second)
        }
      }
    )
  }

}