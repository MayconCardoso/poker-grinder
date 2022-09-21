package com.mctech.pokergrinder.tournament.data.di

import com.mctech.pokergrinder.tournament.data.TournamentRepositoryImpl
import com.mctech.pokergrinder.tournaments.domain.TournamentRepository
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
internal abstract class TournamentDataInjection {

  @Binds
  @Singleton
  abstract fun providesTournamentRepository(
    implementation: TournamentRepositoryImpl,
  ): TournamentRepository

}