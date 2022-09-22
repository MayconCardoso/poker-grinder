package com.mctech.pokergrinder.bankroll.data

import com.mctech.pokergrind.threading.CoroutineDispatchers
import com.mctech.pokergrinder.bankroll.data.database.BankrollTransactionDao
import com.mctech.pokergrinder.bankroll.data.mapper.asBusinessTransaction
import com.mctech.pokergrinder.bankroll.data.mapper.asBusinessTransactions
import com.mctech.pokergrinder.bankroll.data.mapper.asDatabaseTransaction
import com.mctech.pokergrinder.bankroll.domain.BankrollRepository
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class BankrollRepositoryImpl @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val bankrollDao: BankrollTransactionDao,
) : BankrollRepository {

  override fun observeBalance(): Flow<Double> {
    return bankrollDao.observeBalance()
  }

  override fun observeTransactions(): Flow<List<BankrollTransaction>> {
    return bankrollDao.observe().map { it.asBusinessTransactions() }
  }

  override suspend fun save(transaction: BankrollTransaction): Unit = withContext(dispatchers.io) {
    bankrollDao.save(transaction.asDatabaseTransaction())
  }

  override suspend fun loadBalance(): Double = withContext(dispatchers.io) {
    bankrollDao.loadBalance()
  }

  override suspend fun load(id: String): BankrollTransaction = withContext(dispatchers.io) {
    bankrollDao.load(id).asBusinessTransaction()
  }

}