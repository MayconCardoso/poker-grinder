package com.mctech.pokergrinder.database

import android.app.Application
import androidx.room.Room
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
internal class PokerGrinderDatabaseInjection {

  @Provides
  @Singleton
  fun providesVideoProjectDatabase(application: Application): PokerGrinderDatabase {
    return Room.databaseBuilder(
      application,
      PokerGrinderDatabase::class.java,
      "poker_grinder"
    ).build()
  }

  @Provides
  @Singleton
  fun providesTournamentDao(db: PokerGrinderDatabase): TournamentDao {
    return db.tournamentDao()
  }

}