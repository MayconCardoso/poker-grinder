package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.pokergrinder.grind.domain.GrindRepository
import com.mctech.pokergrinder.grind.domain.entities.Session
import javax.inject.Inject

class CreateNewSessionUseCase @Inject constructor(
  private val repository: GrindRepository,
  private val generateUniqueIdUseCase: GenerateUniqueIdUseCase,
) {
  suspend operator fun invoke(title: String) {
    // Gets current session.
    val currentSession = repository.loadCurrentSession()

    // Close session if it's opened.
    if (currentSession != null) {
      repository.saveGrind(currentSession.copy(isOpened = false))
    }

    // Create session
    val session = Session(
      id = generateUniqueIdUseCase.invoke(),
      cash = 0.0,
      buyIn = 0.0,
      title = title,
      isOpened = true,
      tournamentsPlayed = 0,
      startTimeInMs = System.currentTimeMillis(),
    )

    // Saves it
    repository.saveGrind(session)
  }
}