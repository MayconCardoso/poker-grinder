package com.mctech.pokergrinder.bankroll.domain.usecases

import com.mctech.pokergrinder.formatter.asFormattedCurrency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Used to observe formatted bankroll balance.
 *
 * @property observeBalanceUseCase bankroll data repository.
 */
class ObserveFormattedBalanceUseCase @Inject constructor(
  private val observeBalanceUseCase: ObserveBalanceUseCase,
) {
  operator fun invoke(): Flow<String> {
    return observeBalanceUseCase().map { balance ->
      balance.asFormattedCurrency()
    }
  }
}