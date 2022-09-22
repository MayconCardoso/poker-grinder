package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import javax.inject.Inject

class DepositUseCase @Inject constructor(
  private val repository: BankrollRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {

  suspend operator fun invoke(
    amount: Double,
    description: String,
    type: BankrollTransactionType,
  ): String {
    // Generates transaction id
    val transactionId = generateUniqueIdUseCase()

    // Creates transaction
    val bankrollTransaction = BankrollTransaction(
      id = transactionId,
      type = type,
      amount = amount,
      description = description,
      dateInMs = System.currentTimeMillis(),
    )

    // Saves
    repository.save(bankrollTransaction)

    // Returns id
    return transactionId
  }
}