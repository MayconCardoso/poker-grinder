package com.mctech.pokergrinder.settings.data.di

import com.mctech.pokergrinder.settings.data.SettingsRepositoryImpl
import com.mctech.pokergrinder.settings.domain.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class SettingsDataInjection {

  @Binds
  @Singleton
  abstract fun providesSettingsRepository(
    implementation: SettingsRepositoryImpl,
  ): SettingsRepository

}