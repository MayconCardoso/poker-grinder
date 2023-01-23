package com.mctech.pokergrinder.ranges.data.di

import com.mctech.pokergrinder.ranges.data.RangesRepositoryImpl
import com.mctech.pokergrinder.ranges.domain.RangesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RangesDataInjection {

  @Binds
  @Singleton
  abstract fun providesRangesRepository(
    implementation: RangesRepositoryImpl,
  ): RangesRepository

}