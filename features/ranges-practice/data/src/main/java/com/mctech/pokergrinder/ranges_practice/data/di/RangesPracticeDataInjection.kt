package com.mctech.pokergrinder.ranges_practice.data.di

import com.mctech.pokergrinder.ranges_practice.data.RangesPracticeRepositoryImpl
import com.mctech.pokergrinder.ranges_practice.domain.RangesPracticeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RangesPracticeDataInjection {

  @Binds
  @Singleton
  abstract fun providesRangesPracticeRepository(
    implementation: RangesPracticeRepositoryImpl,
  ): RangesPracticeRepository

}