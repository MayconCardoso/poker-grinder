package com.mctech.pokergrinder.bankroll.data.di

import com.mctech.pokergrinder.bankroll.data.BankrollRepositoryImpl
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class BankrollDataInjection {

  @Binds
  @Singleton
  abstract fun providesBankrollRepository(
    implementation: BankrollRepositoryImpl,
  ): BankrollRepository

}