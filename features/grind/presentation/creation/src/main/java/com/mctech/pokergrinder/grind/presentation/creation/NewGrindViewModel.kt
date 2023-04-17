package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.formatter.asFormattedDate
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.usecase.CreateNewSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class NewGrindViewModel @Inject constructor(
  private val analytics: GrindAnalytics,
  private val createNewSessionUseCase: CreateNewSessionUseCase,
) : BaseViewModel() {

  private val _componentState by lazy { MutableStateFlow(initialState()) }
  val componentState: StateFlow<NewGrindState> by lazy { _componentState }

  private fun initialState() = NewGrindState(
    suggestedTitle = Calendar.getInstance().timeInMillis.asFormattedDate()
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