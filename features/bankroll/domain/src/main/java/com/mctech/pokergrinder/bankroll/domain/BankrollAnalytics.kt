package com.mctech.pokergrinder.bankroll.domain

import com.mctech.pokergrinder.analytics.core.AnalyticsEvent
import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import javax.inject.Inject

/**
 * Used to send analytics for the feature.
 */
class BankrollAnalytics @Inject constructor(
  private val analyticsSender: AnalyticsSender,
) {
  suspend fun onDepositScreenViewed() {
    analyticsSender.send(event = AnalyticsEvent(name = "bankroll_screen_deposit_viewed"))
  }

  suspend fun onDepositMade(amount: Double) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "bankroll_screen_deposit_made",
        data = listOf(
          "amount" to amount.toString()
        )
      )
    )
  }

  suspend fun onWithdrawScreenViewed() {
    analyticsSender.send(event = AnalyticsEvent(name = "bankroll_screen_withdraw_viewed"))
  }

  suspend fun onWithdrawMade(amount: Double) {
    analyticsSender.send(
      event = AnalyticsEvent(
        name = "bankroll_screen_withdraw_made",
        data = listOf(
          "amount" to amount.toString()
        )
      )
    )
  }
}