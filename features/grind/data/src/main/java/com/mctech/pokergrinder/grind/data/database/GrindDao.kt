package com.mctech.pokergrinder.grind.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GrindDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun save(vararg item: SessionRoomEntity)

  @Query("SELECT * from grind_session_detail WHERE id = :sessionId")
  fun observeGrind(sessionId: String): Flow<SessionDetailRoomEntity>

  @Query("SELECT * from grind_session_detail WHERE isOpened = 1")
  fun observeCurrentGrind(): Flow<SessionDetailRoomEntity?>

  @Query("SELECT * from grind_session_detail ORDER BY startTimeInMs DESC")
  fun observeAllGrind(): Flow<List<SessionDetailRoomEntity>>

  @Query("SELECT * from grind_session_detail WHERE id = :id")
  suspend fun loadGrind(id: String): SessionDetailRoomEntity

  @Query("SELECT * from grind_session_detail WHERE isOpened = 1")
  suspend fun loadCurrentGrind(): SessionDetailRoomEntity?

  @Query("SELECT * from grind_session ORDER BY startTimeInMs DESC")
  fun loadAll(): List<SessionRoomEntity>

  @Query("DELETE from grind_session")
  fun deleteAll()
}
