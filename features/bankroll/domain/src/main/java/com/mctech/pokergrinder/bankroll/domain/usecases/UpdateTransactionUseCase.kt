package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import javax.inject.Inject

/**
 * Used to update a bankroll transactions.
 *
 * @property repository bankroll data repository.
 */
class UpdateTransactionUseCase @Inject constructor(
  private val repository: BankrollRepository,
) {

  suspend operator fun invoke(amount: Double, id: String) {
    // Gets transaction
    val transaction = repository.load(id)

    // Resolves balance to make sure it's negative
    val resolvedAmount = if (transaction.type == BankrollTransactionType.BUY_IN && amount > 0) {
      amount * -1
    } else {
      amount
    }

    // Updates transaction
    val updatedTransaction = transaction.copy(amount = resolvedAmount)

    // Saves
    repository.save(updatedTransaction)
  }
}