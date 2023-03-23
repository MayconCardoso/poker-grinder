package com.mctech.pokergrinder.bankroll.presentation.list

/**
 * Used to consume events sent from the transaction button.
 */
fun interface BankrollComponentEventConsumer {

  /**
   * Called whenever a new [BankrollComponentEvent] is sent.
   */
  fun onNewEvent(event: BankrollComponentEvent)
}