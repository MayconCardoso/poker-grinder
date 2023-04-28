package com.mctech.pokergrinder.grind_tournament.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GrindTournamentDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveTournament(item: SessionTournamentRoomEntity)

  @Query("SELECT * from grind_session_tournament WHERE idSession = :sessionId ORDER BY startTimeInMs DESC")
  fun observeGrindTournaments(sessionId: String): Flow<List<SessionTournamentRoomEntity>>

  @Query("SELECT * from grind_session_tournament WHERE id = :id")
  suspend fun loadGrindTournament(id: String): SessionTournamentRoomEntity

  @Query("SELECT * from grind_session_tournament ORDER BY startTimeInMs DESC")
  fun loadAll(): List<SessionTournamentRoomEntity>
}
