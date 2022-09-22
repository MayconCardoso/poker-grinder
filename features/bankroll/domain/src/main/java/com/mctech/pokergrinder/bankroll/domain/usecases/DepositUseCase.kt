package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import javax.inject.Inject

class DepositUseCase @Inject constructor(
  private val repository: BankrollRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {

  suspend fun invoke(amount: Double, description: String, type: BankrollTransactionType) {
    // Creates transaction
    val bankrollTransaction = BankrollTransaction(
      id = generateUniqueIdUseCase(),
      type = type,
      amount = amount,
      description = description,
      dateInMs = System.currentTimeMillis(),
    )

    // Saves
    repository.save(bankrollTransaction)
  }
}