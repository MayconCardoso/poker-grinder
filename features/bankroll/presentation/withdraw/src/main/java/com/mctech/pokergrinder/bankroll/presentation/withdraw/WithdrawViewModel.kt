package com.mctech.pokergrinder.bankroll.presentation.withdraw

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import com.mctech.pokergrinder.bankroll.domain.usecases.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class WithdrawViewModel @Inject constructor(
  private val withdrawUseCase: WithdrawUseCase,
) : BaseViewModel() {

  @OnInteraction(WithdrawInteraction.SaveDeposit::class)
  private suspend fun saveDepositInteraction(interaction: WithdrawInteraction.SaveDeposit) {
    try {
      // Saves deposit
      withdrawUseCase(
        amount = interaction.amount,
        type = BankrollTransactionType.WITHDRAW,
        description = interaction.title,
      )

      // Closes screen
      sendCommand(WithdrawCommand.CloseScreen)
    }
    catch (exception: Exception) {
      if(exception is BankrollException.InsufficientBalance) {
        sendCommand(WithdrawCommand.InsufficientBalanceError)
      }
    }
  }

}