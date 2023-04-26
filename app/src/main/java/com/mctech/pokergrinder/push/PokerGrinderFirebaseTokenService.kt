package com.mctech.pokergrinder.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class PokerGrinderFirebaseTokenService : FirebaseMessagingService() {
  override fun onNewToken(token: String) {
    Log.d("PokerGrinderFirebaseTokenService", "Refreshed token: $token")
  }
}