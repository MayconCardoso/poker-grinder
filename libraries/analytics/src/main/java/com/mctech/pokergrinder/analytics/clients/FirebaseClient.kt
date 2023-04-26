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
      when (it.second) {
        is Int -> eventData.putInt(it.first, it.second as Int)
        is Float -> eventData.putFloat(it.first, it.second as Float)
        is Double -> eventData.putDouble(it.first, it.second as Double)
        else -> eventData.putString(it.first, it.second.toString())
      }
    }
    analytics.logEvent(event.name, eventData)
  }

}