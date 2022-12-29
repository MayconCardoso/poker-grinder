package com.mctech.pokergrinder.tournament.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(tournament: TournamentRoomEntity)

  @Delete
  fun delete(tournament: TournamentRoomEntity)

  @Query("SELECT * from tournament ORDER BY start_time")
  fun observe(): Flow<List<TournamentRoomEntity>>

  @Query("SELECT * from tournament where title = :title LIMIT 1")
  fun loadByTitle(title: String): TournamentRoomEntity?
}
