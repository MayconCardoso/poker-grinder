package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.formatter.asFormattedDate
import java.util.Calendar
import javax.inject.Inject

/**
 * Responsible for suggesting a new session name.
 */
class NewSessionsSuggestedNameUseCase @Inject constructor() {
  operator fun invoke(): String = Calendar.getInstance().timeInMillis.asFormattedDate()
}