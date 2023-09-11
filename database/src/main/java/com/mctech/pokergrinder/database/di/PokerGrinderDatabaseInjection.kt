package com.mctech.pokergrinder.database.di

import android.app.Application
import androidx.room.Room
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.database.PokerGrinderDatabase
import com.mctech.pokergrinder.database.PokerGrinderDatabaseInitialization
import com.mctech.pokergrinder.database.PokerGrinderDatabaseMigrations
import com.mctech.pokergrinder.grind.data.database.GrindDao
import com.mctech.pokergrinder.grind_gameplay.data.database.GrindGameplayDao
import com.mctech.pokergrinder.grind_tournament.data.database.GrindTournamentDao
import com.mctech.pokergrinder.ranges_practice.data.database.RangePracticeDao
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
class PokerGrinderDatabaseInjection {

  @Provides
  @Singleton
  fun providesPokerGrinderDatabase(application: Application): PokerGrinderDatabase {
    return Room.databaseBuilder(application, PokerGrinderDatabase::class.java, "poker_grinder")
      .addCallback(PokerGrinderDatabaseInitialization)
      .addMigrations(*PokerGrinderDatabaseMigrations.all)
      .build()
  }

  @Provides
  @Singleton
  fun providesTournamentDao(db: PokerGrinderDatabase): TournamentDao {
    return db.tournamentDao()
  }

  @Provides
  @Singleton
  fun providesBankrollTransactionDao(db: PokerGrinderDatabase): BankrollTransactionDao {
    return db.bankrollTransactionDao()
  }

  @Provides
  @Singleton
  fun providesGrindDao(db: PokerGrinderDatabase): GrindDao {
    return db.grindDao()
  }

  @Provides
  @Singleton
  fun providesGrindGameplay(db: PokerGrinderDatabase): GrindGameplayDao {
    return db.grindGameplayDao()
  }

  @Provides
  @Singleton
  fun providesGrindTournamentDao(db: PokerGrinderDatabase): GrindTournamentDao {
    return db.grindTournamentDao()
  }

  @Provides
  @Singleton
  fun providesRangePracticeDao(db: PokerGrinderDatabase): RangePracticeDao {
    return db.rangePracticeDao()
  }

  @Provides
  @Singleton
  fun providesSummaryDao(db: PokerGrinderDatabase): SummaryDao {
    return db.summaryDao()
  }

  @Provides
  @Singleton
  fun providesSettingsDao(db: PokerGrinderDatabase): SettingsDao {
    return db.settingsDao()
  }

}