package com.mctech.pokergrinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import com.mctech.pokergrinder.tournament.data.database.TournamentRoomEntity

@Database(
  version = 1,
  entities = [
    TournamentRoomEntity::class,
  ],
)
public abstract class PokerGrinderDatabase : RoomDatabase() {
  public abstract fun tournamentDao(): TournamentDao
}
