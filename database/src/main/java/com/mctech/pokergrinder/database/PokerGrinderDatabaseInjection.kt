package com.mctech.pokergrinder.database

import android.app.Application
import androidx.room.Room
import com.mctech.pokergrinder.tournament.data.database.TournamentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
public class PokerGrinderDatabaseInjection {

  @Provides
  @Singleton
  public fun providesVideoProjectDatabase(application: Application): PokerGrinderDatabase {
    return Room.databaseBuilder(
      application,
      PokerGrinderDatabase::class.java,
      "poker_grinder"
    ).build()
  }

  @Provides
  @Singleton
  public fun providesTournamentDao(db: PokerGrinderDatabase): TournamentDao {
    return db.tournamentDao()
  }

}