package com.mctech.pokergrinder.bankroll.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

/**
 * Used to observe formatted bankroll balance.
 *
 * @property observeBalanceUseCase bankroll data repository.
 */
class ObserveFormattedBalanceUseCase @Inject constructor(
  private val observeBalanceUseCase: ObserveBalanceUseCase,
) {

  private val formatter by lazy {
    DecimalFormat(
      "$#0.00",
      DecimalFormatSymbols(Locale.ENGLISH)
    )
  }

  operator fun invoke(): Flow<String> {
    return observeBalanceUseCase().map { balance ->
      formatter.format(balance)
    }
  }
}