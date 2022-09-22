package com.mctech.pokergrinder.grind.data.di

import com.mctech.pokergrinder.grind.data.GrindRepositoryImpl
import com.mctech.pokergrinder.grind.domain.GrindRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class GrindDataInjection {

  @Binds
  @Singleton
  abstract fun providesGrindRepository(
    implementation: GrindRepositoryImpl,
  ): GrindRepository

}