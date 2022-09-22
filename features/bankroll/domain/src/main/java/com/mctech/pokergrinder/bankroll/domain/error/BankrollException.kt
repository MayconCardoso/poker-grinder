package com.mctech.pokergrinder.bankroll.domain.error

sealed class BankrollException(reason: String? = null) : RuntimeException(reason) {
  object InsufficientBalance : BankrollException()
}