package com.mctech.pokergrinder.ranges_practice.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO responsible for saving range practice data on database.
 */
@Dao
interface RangePracticeDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(item: RangePracticeRoomEntity)

  @Query("SELECT * from range_practice ORDER BY dateInMs DESC LIMIT 1000")
  fun observe(): Flow<List<RangePracticeRoomEntity>>
}
