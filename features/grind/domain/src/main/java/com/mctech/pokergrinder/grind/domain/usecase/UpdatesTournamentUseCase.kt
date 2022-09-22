package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.domain.usecases.DepositUseCase
import com.mctech.pokergrinder.bankroll.domain.usecases.UpdateTransactionUseCase
import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import javax.inject.Inject

class UpdatesTournamentUseCase @Inject constructor(
  private val repository: GrindRepository,
  private val depositUseCase: DepositUseCase,
  private val updateTransactionUseCase: UpdateTransactionUseCase,
) {
  suspend operator fun invoke(sessionTournament: SessionTournament) {
    // Checks if session tournament exists
    if (sessionTournament.id.isBlank()) {
      error("Session tournament does not exist.")
    }

    // Loads saved tournament
    val session = repository.loadGrind(sessionTournament.idSession)
    val saveTournament = repository.loadGrindTournament(sessionTournament.id)

    // Computes diff of profit
    val diffProfit = sessionTournament.profit - saveTournament.profit
    val diffBuyIn = sessionTournament.buyIn - saveTournament.buyIn

    // Updates buy in bankroll transaction with new value.
    updatesBuyInTransaction(sessionTournament)

    // Updates profit bankroll transaction with new value.
    val transactionId = updatesProfitTransaction(sessionTournament)

    // Updates Tournament
    repository.saveGrindTournament(
      sessionTournament.copy(
        idTransactionProfit = transactionId
      )
    )

    // Updates Session
    repository.saveGrind(session.copy(outcome = session.outcome + diffProfit + diffBuyIn))
  }

  private suspend fun updatesBuyInTransaction(item: SessionTournament) {
    updateTransactionUseCase(
      id = item.idTransactionBuyIn,
      amount = item.buyIn,
    )
  }

  private suspend fun updatesProfitTransaction(item: SessionTournament): String? {
    // Profit has not been updated and do not exist.
    if (item.idTransactionProfit == null && item.profit <= 0) {
      return null
    }

    // Profit already exists, so we update it
    if (item.idTransactionProfit != null) {
      updateTransactionUseCase(
        id = item.idTransactionProfit,
        amount = item.profit,
      )
      return item.idTransactionProfit
    }

    // Project does not exist but is being reported.
    return depositUseCase.invoke(
      amount = item.profit,
      description = item.title,
      type = BankrollTransactionType.PROFIT,
    )
  }
}