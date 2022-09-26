package com.mctech.pokergrinder.grind.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface GrindDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun save(session: SessionRoomEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun saveTournament(sessionTournament: SessionTournamentRoomEntity)

  @Query("SELECT * from grind_session_detail WHERE id = :sessionId")
  public fun observeGrind(sessionId: String): Flow<SessionDetailRoomEntity>

  @Query("SELECT * from grind_session_tournament WHERE idSession = :sessionId ORDER BY startTimeInMs DESC")
  public fun observeGrindTournaments(sessionId: String): Flow<List<SessionTournamentRoomEntity>>

  @Query("SELECT * from grind_session_detail WHERE isOpened = 1")
  public fun observeCurrentGrind(): Flow<SessionDetailRoomEntity?>

  @Query("SELECT * from grind_session_detail ORDER BY startTimeInMs DESC")
  public fun observeAllGrind(): Flow<List<SessionDetailRoomEntity>>

  @Query("SELECT * from grind_session_detail WHERE id = :id")
  public suspend fun loadGrind(id: String): SessionDetailRoomEntity

  @Query("SELECT * from grind_session_tournament WHERE id = :id")
  public suspend fun loadGrindTournament(id: String): SessionTournamentRoomEntity

  @Query("SELECT * from grind_session_detail WHERE isOpened = 1")
  public suspend fun loadCurrentGrind(): SessionDetailRoomEntity?

}
