package com.mctech.pokergrinder.bankroll.presentation.creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.DepositUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DepositViewModel @Inject constructor(
  private val depositUseCase: DepositUseCase,
) : BaseViewModel() {

  @OnInteraction(com.mctech.pokergrinder.bankroll.presentation.creation.DepositInteraction.SaveDeposit::class)
  private suspend fun saveDepositInteraction(interaction: com.mctech.pokergrinder.bankroll.presentation.creation.DepositInteraction.SaveDeposit) {
    // Saves deposit
    depositUseCase(
      amount = interaction.amount,
      type = BankrollTransactionType.DEPOSIT,
      description = interaction.title,
    )

    // Closes screen
    sendCommand(com.mctech.pokergrinder.bankroll.presentation.creation.TournamentCommand.CloseScreen)
  }

}