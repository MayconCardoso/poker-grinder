package com.mctech.pokergrinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionRoomEntity
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind.data.database.SessionDetailRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentFlipRoomEntity
import com.mctech.pokergrinder.grind.data.database.SessionTournamentRoomEntity
import com.mctech.pokergrinder.settings.data.database.SettingsDao
import com.mctech.pokergrinder.settings.data.database.SettingsRoomEntity
import com.mctech.pokergrinder.summary.data.database.SummaryDao
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import com.mctech.pokergrinder.tournament.data.database.TournamentRoomEntity

@Database(
  version = 5,
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
  ],
  views = [
    SessionDetailRoomEntity::class,
  ]
)
public abstract class PokerGrinderDatabase : RoomDatabase() {
  public abstract fun grindDao(): GrindDao
  public abstract fun summaryDao(): SummaryDao
  public abstract fun settingsDao(): SettingsDao
  public abstract fun tournamentDao(): TournamentDao
  public abstract fun bankrollTransactionDao(): BankrollTransactionDao
}
