package com.mctech.pokergrinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind_gameplay.data.database.GrindGameplayDao
import com.mctech.pokergrinder.grind_gameplay.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind_tournament.data.database.GrindTournamentDao
import com.mctech.pokergrinder.grind_tournament.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeDao
import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeRoomEntity
import com.mctech.pokergrinder.settings.data.database.SettingsDao
import com.mctech.pokergrinder.settings.data.database.SettingsRoomEntity
import com.mctech.pokergrinder.summary.data.database.SummaryDao
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import com.mctech.pokergrinder.tournament.data.database.TournamentRoomEntity

@Database(
  version = 7,
  entities = [
    // Settings
    SettingsRoomEntity::class,

    // Session
    SessionRoomEntity::class,
    SessionTournamentRoomEntity::class,
    SessionTournamentFlipRoomEntity::class,

    // Tournaments
    TournamentRoomEntity::class,

    // Bankroll
    BankrollTransactionRoomEntity::class,

    // Bankroll
    RangePracticeRoomEntity::class,
  ],
  views = [
    SessionDetailRoomEntity::class,
  ]
)
abstract class PokerGrinderDatabase : RoomDatabase() {
  abstract fun grindDao(): GrindDao
  abstract fun grindGameplayDao(): GrindGameplayDao
  abstract fun grindTournamentDao(): GrindTournamentDao
  abstract fun summaryDao(): SummaryDao
  abstract fun settingsDao(): SettingsDao
  abstract fun tournamentDao(): TournamentDao
  abstract fun rangePracticeDao(): RangePracticeDao
  abstract fun bankrollTransactionDao(): BankrollTransactionDao
}
