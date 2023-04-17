package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import java.util.Calendar
import javax.inject.Inject

/**
 * Responsible for making a bankroll withdraw.
 *
 * @property repository bankroll data repository.
 * @property generateUniqueIdUseCase use case responsible for generating a unique id.
 */
class WithdrawUseCase @Inject constructor(
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
    // Resolves balance to make sure it's negative
    val resolvedAmount = if(amount > 0) amount * -1 else amount

    // Generates transaction id
    val transactionId = generateUniqueIdUseCase()

    // Creates transaction
    val bankrollTransaction = BankrollTransaction(
      id = transactionId,
      type = type,
      amount = resolvedAmount,
      description = description,
      dateInMs = Calendar.getInstance().timeInMillis,
    )

    // Saves
    repository.save(bankrollTransaction)

    // Returns id
    return transactionId
  }
}