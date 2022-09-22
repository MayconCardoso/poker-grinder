package com.mctech.pokergrinder.bankroll.data.mapper

import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType

/**
 * Converts a list of transaction database entity onto a business one known by the modules.
 */
internal fun List<BankrollTransactionRoomEntity>.asBusinessTransactions(): List<BankrollTransaction> {
  return this.map { dbEntity ->
    dbEntity.asBusinessTransaction()
  }
}

/**
 * Converts a transaction database entity onto a business one known by the modules.
 */
internal fun BankrollTransactionRoomEntity.asBusinessTransaction() = BankrollTransaction(
  id = id,
  type = BankrollTransactionType.valueOf(type),
  amount = amount,
  description = description,
  dateInMs = dateInMs,
)

/**
 * Converts a business transaction onto a database one.
 */
internal fun BankrollTransaction.asDatabaseTransaction() = BankrollTransactionRoomEntity(
  id = id,
  type = type.name,
  amount = amount,
  description = description,
  dateInMs = dateInMs,
)
