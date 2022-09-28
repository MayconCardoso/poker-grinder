package com.mctech.pokergrinder.database

import android.app.Application
import androidx.room.Room
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.settings.data.database.SettingsDao
import com.mctech.pokergrinder.summary.data.database.SummaryDao
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
  public fun providesPokerGrinderDatabase(application: Application): PokerGrinderDatabase {
    return Room.databaseBuilder(application, PokerGrinderDatabase::class.java, "poker_grinder")
      .addMigrations(*PokerGrinderDatabaseMigrations.all)
      .build()
  }

  @Provides
  @Singleton
  public fun providesTournamentDao(db: PokerGrinderDatabase): TournamentDao {
    return db.tournamentDao()
  }

  @Provides
  @Singleton
  public fun providesBankrollTransactionDao(db: PokerGrinderDatabase): BankrollTransactionDao {
    return db.bankrollTransactionDao()
  }

  @Provides
  @Singleton
  public fun providesGrindDao(db: PokerGrinderDatabase): GrindDao {
    return db.grindDao()
  }

  @Provides
  @Singleton
  public fun providesSummaryDao(db: PokerGrinderDatabase): SummaryDao {
    return db.summaryDao()
  }

  @Provides
  @Singleton
  public fun providesSettingsDao(db: PokerGrinderDatabase): SettingsDao {
    return db.settingsDao()
  }

}