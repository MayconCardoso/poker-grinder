package com.mctech.pokergrinder.bankroll.domain.usecases

import java.util.UUID
import javax.inject.Inject

class GenerateUniqueIdUseCase @Inject constructor() {
  operator fun invoke() = UUID.randomUUID().toString()
}