package com.mctech.pokergrinder.summary.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface SummaryDao {
  @Query("SELECT  COUNT(1) AS total, SUM(tournaments) as tournaments, (SELECT COUNT(1) FROM grind_session_detail WHERE (cash - buyIn) >= 0) as upDays, (SELECT COUNT(1) FROM grind_session_detail WHERE (cash - buyIn) < 0) as downDays FROM grind_session_detail")
  public fun observeSessionSummary(): Flow<SessionSummaryRoomEntity>

  @Query("SELECT SUM(profit - buyIn) AS profit, SUM(profit) AS cash, SUM(buyIn) AS buyIn FROM grind_session_tournament")
  public fun observeInvestmentSummary(): Flow<InvestmentSummaryRoomEntity>

  @Query("SELECT title, COUNT(1) AS tournaments, SUM(profit) AS cash, SUM(buyIn) AS buyIn, SUM(profit - buyIn) AS profit FROM grind_session_tournament GROUP BY title ORDER BY profit DESC")
  public fun observeTournamentSummary(): Flow<List<TournamentSummaryRoomEntity>>
}
