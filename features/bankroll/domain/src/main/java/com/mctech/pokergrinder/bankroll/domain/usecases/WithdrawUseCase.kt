package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.error.BankrollException
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
  private val repository: BankrollRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {

  suspend operator fun invoke(amount: Double, description: String, type: BankrollTransactionType) {
    // Gets current balance
    val balance = repository.loadBalance()

    // Checks if has balance to withdraw.
    if(balance < amount) {
      throw BankrollException.InsufficientBalance
    }

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