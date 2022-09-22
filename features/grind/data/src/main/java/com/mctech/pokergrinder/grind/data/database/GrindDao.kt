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

  @Query("SELECT * from grind_session WHERE id = :sessionId")
  public fun observeGrind(sessionId: String): Flow<SessionRoomEntity>

  @Query("SELECT * from grind_session_tournament WHERE idSession = :sessionId ORDER BY startTimeInMs DESC")
  public fun observeGrindTournaments(sessionId: String): Flow<List<SessionTournamentRoomEntity>>

  @Query("SELECT * from grind_session WHERE isOpened = 1")
  public fun observeCurrentGrind(): Flow<SessionRoomEntity?>

  @Query("SELECT * from grind_session ORDER BY startTimeInMs DESC")
  public fun observeAllGrind(): Flow<List<SessionRoomEntity>>

  @Query("SELECT * from grind_session WHERE id = :id")
  public suspend fun loadGrind(id: String): SessionRoomEntity

  @Query("SELECT * from grind_session_tournament WHERE id = :id")
  public suspend fun loadGrindTournament(id: String): SessionTournamentRoomEntity

  @Query("SELECT * from grind_session WHERE isOpened = 1")
  public suspend fun loadCurrentGrind(): SessionRoomEntity?

}
