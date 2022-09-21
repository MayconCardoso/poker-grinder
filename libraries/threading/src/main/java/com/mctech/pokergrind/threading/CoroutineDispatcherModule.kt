package com.mctech.pokergrind.threading

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@InstallIn(dagger.hilt.components.SingletonComponent::class)
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
