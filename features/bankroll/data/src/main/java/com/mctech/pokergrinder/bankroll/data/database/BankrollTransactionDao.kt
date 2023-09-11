package com.mctech.pokergrinder.bankroll.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO responsible for saving bankroll transaction data on database.
 */
@Dao
interface BankrollTransactionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(vararg transaction: BankrollTransactionRoomEntity)

  @Query("SELECT * from bankroll_transaction ORDER BY dateInMs DESC")
  fun observe(): Flow<List<BankrollTransactionRoomEntity>>

  @Query("SELECT * from bankroll_transaction WHERE id = :id")
  suspend fun load(id: String): BankrollTransactionRoomEntity

  @Query("SELECT COALESCE(SUM(amount), 0) from bankroll_transaction")
  fun observeBalance(): Flow<Double>

  @Query("SELECT COALESCE(SUM(amount), 0) from bankroll_transaction")
  suspend fun loadBalance(): Double

  @Query("SELECT * from bankroll_transaction ORDER BY dateInMs DESC")
  fun loadAll(): List<BankrollTransactionRoomEntity>

  @Query("DELETE from bankroll_transaction")
  fun deleteAll()
}
