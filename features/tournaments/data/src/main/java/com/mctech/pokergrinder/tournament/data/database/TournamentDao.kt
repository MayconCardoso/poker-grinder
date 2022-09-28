package com.mctech.pokergrinder.tournament.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface TournamentDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun save(tournament: TournamentRoomEntity)

  @Delete
  public fun delete(tournament: TournamentRoomEntity)

  @Query("SELECT * from tournament ORDER BY start_time")
  public fun observe(): Flow<List<TournamentRoomEntity>>
}
