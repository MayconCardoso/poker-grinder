package com.mctech.pokergrinder.grind_gameplay.data.di

import com.mctech.pokergrinder.grind_gameplay.data.GrindGameplayRepositoryImpl
import com.mctech.pokergrinder.grind_gameplay.domain.GrindGameplayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class GrindGameplayDataInjection {

  @Binds
  @Singleton
  abstract fun providesGrindRepository(
    implementation: GrindGameplayRepositoryImpl,
  ): GrindGameplayRepository

}