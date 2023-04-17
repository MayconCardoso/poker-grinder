package com.mctech.pokergrinder.bankroll.presentation.withdraw

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.bankroll.domain.BankrollAnalytics
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class WithdrawViewModel @Inject constructor(
  private val analytics: BankrollAnalytics,
  private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel() {

  @OnInteraction(WithdrawInteraction.SaveWithdraw::class)
  private suspend fun saveWithdrawInteraction(interaction: WithdrawInteraction.SaveWithdraw) {
    // Saves deposit
    withdrawUseCase(
      amount = interaction.amount,
      type = BankrollTransactionType.WITHDRAW,
      description = interaction.title,
    )

    // Analytics
    analytics.onWithdrawMade(amount = interaction.amount)

    // Closes screen
    sendCommand(WithdrawCommand.CloseScreen)
  }

}