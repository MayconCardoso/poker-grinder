package com.mctech.pokergrinder.grind.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mctech.pokergrinder.grind.domain.entities.SessionTournamentFlip
import kotlinx.coroutines.flow.Flow

@Dao
public interface GrindDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun save(item: SessionRoomEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun saveTournament(item: SessionTournamentRoomEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun saveTournamentFlip(item: SessionTournamentFlipRoomEntity)

  @Query("SELECT * from grind_session_detail WHERE id = :sessionId")
  public fun observeGrind(sessionId: String): Flow<SessionDetailRoomEntity>

  @Query("SELECT * from grind_session_tournament WHERE idSession = :sessionId ORDER BY startTimeInMs DESC")
  public fun observeGrindTournaments(sessionId: String): Flow<List<SessionTournamentRoomEntity>>

  @Query("SELECT * from grind_session_tournament_flip WHERE idSession = :sessionId")
  public fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlipRoomEntity>>

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
