package com.mctech.pokergrinder.summary.data.di

import com.mctech.pokergrinder.summary.data.SummaryRepositoryImpl
import com.mctech.pokergrinder.summary.domain.SummaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class SummaryDataInjection {

  @Binds
  @Singleton
  abstract fun providesSummaryRepository(
    implementation: SummaryRepositoryImpl,
  ): SummaryRepository

}