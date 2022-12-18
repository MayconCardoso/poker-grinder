package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used to observe bankroll transactions.
 *
 * @property repository bankroll data repository.
 */
class ObserveTransactionsUseCase @Inject constructor(
  private val repository: BankrollRepository,
) {

  operator fun invoke(): Flow<List<BankrollTransaction>> {
    return repository.observeTransactions()
  }
}