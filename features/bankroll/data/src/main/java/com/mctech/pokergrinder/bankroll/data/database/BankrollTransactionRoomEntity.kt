package com.mctech.pokergrinder.bankroll.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "bankroll_transaction",
  indices = [
    Index(
      value = ["type"],
      name = "bankroll_transaction_type_index",
    ),
    Index(
      value = ["dateInMs"],
      name = "bankroll_transaction_date_index",
    ),
  ]
)
public data class BankrollTransactionRoomEntity(
  @PrimaryKey
  val id: String,
  val type: String,
  val amount: Double,
  val description: String,
  val dateInMs: Long,
)
