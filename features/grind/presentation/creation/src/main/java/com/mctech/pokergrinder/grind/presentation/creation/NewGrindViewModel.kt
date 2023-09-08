package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.usecase.CreateNewSessionUseCase
import com.mctech.pokergrinder.grind.domain.usecase.NewSessionsSuggestedNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class NewGrindViewModel @Inject constructor(
  private val analytics: GrindAnalytics,
  private val createNewSessionUseCase: CreateNewSessionUseCase,
  private val sessionNameSuggestionUseCase: NewSessionsSuggestedNameUseCase,
) : BaseViewModel() {

  private val _componentState by lazy { MutableStateFlow<NewGrindState?>(null) }
  val componentState: StateFlow<NewGrindState?> by lazy { _componentState }

  override suspend fun initializeComponents() {
    _componentState.value = initialState()
  }

  private fun initialState() = NewGrindState(
    suggestedTitle = sessionNameSuggestionUseCase(),
  )

  @OnInteraction(NewGrindInteraction.SaveGrind::class)
  private suspend fun saveTournamentInteraction(interaction: NewGrindInteraction.SaveGrind) {
    // Saves new grind
    createNewSessionUseCase(title = interaction.title)

    // Analytics
    analytics.onSessionCreated(title = interaction.title)

    // Closes screen
    sendCommand(NewGrindCommand.CloseScreen)
  }

}