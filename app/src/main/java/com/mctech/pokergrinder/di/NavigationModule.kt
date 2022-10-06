package com.mctech.pokergrinder.di

import com.mctech.pokergrinder.PokerGrinderNavigator
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollNavigation
import com.mctech.pokergrinder.grind.presentation.GrindNavigation
import com.mctech.pokergrinder.tournament.presentation.TournamentNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NavigationModule {

  @Binds
  @Singleton
  abstract fun bindsGrindNavigation(appNavigator: PokerGrinderNavigator): GrindNavigation

  @Binds
  @Singleton
  abstract fun bindsBankrollNavigation(appNavigator: PokerGrinderNavigator): BankrollNavigation

  @Binds
  @Singleton
  abstract fun bindsTournamentNavigator(appNavigator: PokerGrinderNavigator): TournamentNavigation

  @InstallIn(SingletonComponent::class)
  @Module
  object AppNavigatorModule {

    @Provides
    @Singleton
    fun providesPokerGrinderNavigator() = PokerGrinderNavigator()
  }
}