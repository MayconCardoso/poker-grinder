package com.mctech.pokergrinder.analytics.clients

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.mctech.pokergrinder.analytics.core.AnalyticsEvent

class FirebaseClient : AnalyticsClient {

  private val analytics by lazy { Firebase.analytics }

  override suspend fun sendEvent(event: AnalyticsEvent) {
    val eventData = Bundle()
    event.data.forEach {
      eventData.putString(it.first, it.second)
    }
    analytics.logEvent(event.name, eventData)
  }

}