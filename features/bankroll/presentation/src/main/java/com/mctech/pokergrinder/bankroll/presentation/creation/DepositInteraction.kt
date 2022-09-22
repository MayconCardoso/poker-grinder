package com.mctech.pokergrinder.bankroll.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction

internal sealed class DepositInteraction : UserInteraction {

  data class SaveDeposit(
    val title: String,
    val amount: Double,
  ) : DepositInteraction()
}