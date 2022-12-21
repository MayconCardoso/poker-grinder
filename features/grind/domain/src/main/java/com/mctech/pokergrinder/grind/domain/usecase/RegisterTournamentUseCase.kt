package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.WithdrawUseCase
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import java.util.Calendar
import javax.inject.Inject

/**
 * Used save a grind tournament.
 *
 * @property repository grind data repository.
 * @property generateUniqueIdUseCase unique id generator.
 */
class RegisterTournamentUseCase @Inject constructor(
  private val repository: GrindRepository,
  private val withdrawUseCase: WithdrawUseCase,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {
  suspend operator fun invoke(session: Session, title: String, buyIn: Double) {
    // Withdraw the money spent on the tournament
    val transactionId = withdrawUseCase.invoke(
      amount = buyIn,
      description = title,
      type = BankrollTransactionType.BUY_IN,
    )

    // Creates tournament
    val tournament = SessionTournament(
      id = generateUniqueIdUseCase(),
      idSession = session.id,
      idTransactionBuyIn = transactionId,
      idTransactionProfit = null,
      buyIn = buyIn,
      title = title,
      profit = 0.0,
      startTimeInMs = Calendar.getInstance().timeInMillis,
      isGrouped = false,
    )

    // Saves updated session.
    repository.saveGrindTournament(tournament)
  }
}