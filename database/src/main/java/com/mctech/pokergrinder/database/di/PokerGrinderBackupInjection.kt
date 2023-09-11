package com.mctech.pokergrinder.database.di

import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.database.backup.BackupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class PokerGrinderBackupInjection {

  @Binds
  @Singleton
  abstract fun providesPokerGrinderBackup(repository: BackupRepositoryImpl): BackupRepository

}