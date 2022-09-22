package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBalanceUseCase @Inject constructor(
  private val repository: BankrollRepository,
) {

  operator fun invoke(): Flow<Double> {
    return repository.observeBalance()
  }
}