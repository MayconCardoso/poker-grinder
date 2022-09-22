package com.mctech.pokergrinder.grind.domain.usecase

import java.util.UUID
import javax.inject.Inject

class GenerateUniqueIdUseCase @Inject constructor() {
  operator fun invoke() = UUID.randomUUID().toString()
}