package com.mctech.pokergrinder.analytics.di

import com.mctech.pokergrinder.analytics.core.AnalyticsSender
import com.mctech.pokergrinder.analytics.sender.AnalyticsSenderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AnalyticsModule {

  @Provides
  @Singleton
  fun providesAnalyticsSender(impl: AnalyticsSenderImpl): AnalyticsSender {
    return impl
  }

}