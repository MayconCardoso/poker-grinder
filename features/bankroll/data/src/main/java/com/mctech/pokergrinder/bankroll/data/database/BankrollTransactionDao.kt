package com.mctech.pokergrinder.bankroll.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface BankrollTransactionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun save(transaction: BankrollTransactionRoomEntity)

  @Query("SELECT * from bankroll_transaction ORDER BY dateInMs DESC")
  public fun observe(): Flow<List<BankrollTransactionRoomEntity>>

  @Query("SELECT COALESCE(SUM(amount), 0) from bankroll_transaction")
  public fun observeBalance(): Flow<Double>

  @Query("SELECT COALESCE(SUM(amount), 0) from bankroll_transaction")
  public fun loadBalance(): Double
}
