package com.mctech.pokergrinder.grind.domain.usecase

import java.util.UUID
import javax.inject.Inject

/**
 * Used to generate a unique ID.
 */
class GenerateUniqueIdUseCase @Inject constructor() {
  operator fun invoke() = UUID.randomUUID().toString()
}