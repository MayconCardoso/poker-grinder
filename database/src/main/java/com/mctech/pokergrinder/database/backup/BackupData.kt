package com.mctech.pokergrinder.database.backup

import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeRoomEntity
import com.mctech.pokergrinder.tournament.data.database.TournamentRoomEntity

data class BackupData(
  // Sessions
  val sessions: List<SessionRoomEntity>,
  val sessionsTournament: List<SessionTournamentRoomEntity>,
  val sessionsTournamentFlips: List<SessionTournamentFlipRoomEntity>,

  // Tournaments
  val tournaments: List<TournamentRoomEntity>,

  // Bankroll
  val bankrollTransactions: List<BankrollTransactionRoomEntity>,

  // Range
  val rangePractice: List<RangePracticeRoomEntity>,
)
