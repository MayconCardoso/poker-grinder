package com.mctech.pokergrinder.grind_tournament.data.di

import com.mctech.pokergrinder.grind_tournament.data.GrindTournamentRepositoryImpl
import com.mctech.pokergrinder.grind_tournament.domain.GrindTournamentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class GrindTournamentDataInjection {

  @Binds
  @Singleton
  abstract fun providesGrindRepository(
    implementation: GrindTournamentRepositoryImpl,
  ): GrindTournamentRepository

}