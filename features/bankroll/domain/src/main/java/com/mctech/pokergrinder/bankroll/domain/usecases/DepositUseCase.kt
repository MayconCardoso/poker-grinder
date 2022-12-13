package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import java.util.Calendar
import javax.inject.Inject

/**
 * Responsible for making a bankroll deposit.
 *
 * @property repository bankroll data repository.
 * @property generateUniqueIdUseCase use case responsible for generating a unique id.
 */
class DepositUseCase @Inject constructor(
  private val repository: BankrollRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {

  /**
   * Creates a deposit transaction and return it's id.
   */
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
      dateInMs = Calendar.getInstance().timeInMillis,
    )

    // Saves
    repository.save(bankrollTransaction)

    // Returns id
    return transactionId
  }
}