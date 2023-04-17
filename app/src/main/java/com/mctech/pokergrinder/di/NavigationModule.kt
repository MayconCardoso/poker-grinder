package com.mctech.pokergrinder.di

import com.mctech.pokergrinder.PokerGrinderNavigator
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.ranges.presentation.navigation.RangeNavigation
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation
import com.mctech.pokergrinder.summary.presentation.navigation.SummaryNavigation
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
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
  abstract fun bindsRangesNavigator(appNavigator: PokerGrinderNavigator): RangeNavigation

  @Binds
  @Singleton
  abstract fun bindsSummaryNavigator(appNavigator: PokerGrinderNavigator): SummaryNavigation

  @Binds
  @Singleton
  abstract fun bindsBankrollNavigation(appNavigator: PokerGrinderNavigator): BankrollNavigation

  @Binds
  @Singleton
  abstract fun bindsTournamentNavigator(appNavigator: PokerGrinderNavigator): TournamentNavigation

  @Binds
  @Singleton
  abstract fun bindsRangePracticeNavigation(appNavigator: PokerGrinderNavigator): RangePracticeNavigation

  @InstallIn(SingletonComponent::class)
  @Module
  object AppNavigatorModule {

    @Provides
    @Singleton
    fun providesPokerGrinderNavigator() = PokerGrinderNavigator()
  }
}