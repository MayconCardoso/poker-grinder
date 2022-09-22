package com.mctech.pokergrinder.bankroll.domain

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import kotlinx.coroutines.flow.Flow

interface BankrollRepository {
  fun observeBalance(): Flow<Double>
  fun observeTransactions(): Flow<List<BankrollTransaction>>
  suspend fun save(transaction: BankrollTransaction)
  suspend fun loadBalance(): Double
  suspend fun load(id: String): BankrollTransaction
}