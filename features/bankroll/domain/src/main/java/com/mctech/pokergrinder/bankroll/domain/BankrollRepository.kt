package com.mctech.pokergrinder.bankroll.domain

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import kotlinx.coroutines.flow.Flow

/**
 * Transaction repository to access transaction data.
 */
interface BankrollRepository {

  /**
   * Observes current bankroll balance.
   */
  fun observeBalance(): Flow<Double>

  /**
   * Observes bankroll transactions.
   */
  fun observeTransactions(): Flow<List<BankrollTransaction>>

  /**
   * Used to save a [transaction].
   */
  suspend fun save(transaction: BankrollTransaction)

  /**
   * Gets the current bankroll balance.
   */
  suspend fun loadBalance(): Double

  /**
   * Loads a [BankrollTransaction] given it's [id]
   */
  suspend fun load(id: String): BankrollTransaction
}