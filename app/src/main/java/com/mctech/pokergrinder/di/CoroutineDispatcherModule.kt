package com.mctech.pokergrinder.di

import com.mctech.pokergrinder.threading.CoroutineDispatchers
import com.mctech.pokergrinder.threading.DefaultCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatcherModule {

  @Singleton
  @Provides
  fun providesCoroutineDispatchers(): CoroutineDispatchers = DefaultCoroutineDispatchers

  @Singleton
  @Provides
  @IoDispatcher
  fun providesIoDispatcher(dispatchers: CoroutineDispatchers): CoroutineDispatcher = dispatchers.io
}
