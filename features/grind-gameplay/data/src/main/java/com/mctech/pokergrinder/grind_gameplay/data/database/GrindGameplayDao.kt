package com.mctech.pokergrinder.grind_gameplay.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GrindGameplayDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveTournamentFlip(item: SessionTournamentFlipRoomEntity)

  @Query("SELECT * from grind_session_tournament_flip WHERE idSession = :sessionId")
  fun observeGrindTournamentFlips(sessionId: String): Flow<List<SessionTournamentFlipRoomEntity>>

}
