package com.mctech.pokergrinder.analytics.core

/**
 * Describes an analytics event.
 */
data class AnalyticsEvent(
  val name: String,
  val data: List<Pair<String, Any>> = listOf()
)