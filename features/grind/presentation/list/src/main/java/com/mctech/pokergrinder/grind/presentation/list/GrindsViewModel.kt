package com.mctech.pokergrinder.grind.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import com.mctech.pokergrinder.grind.presentation.list.adapter.GrindAdapterConsumerEvent
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.domain.usecase.ObserveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindsViewModel @Inject constructor(
  private val analytics: GrindAnalytics,
  private val observeSettingsUseCase: ObserveSettingsUseCase,
  private val observeAllGrindsUseCase: ObserveAllGrindsUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<GrindState>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<GrindState>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeAllGrindsUseCase()
      .combine(observeSettingsUseCase(SettingsAvailable.HIDE_BANKROLL_BALANCE)) { sessions, settings ->
        val shouldHideBalance = settings?.asBoolean() == true
        sessions.map { session ->
          GrindState(
            session = session,
            title = session.title,
            tournaments = session.tournamentsPlayed,
            cash = if (shouldHideBalance) "*****" else session.formattedCash(),
            buyIn = if (shouldHideBalance) "*****" else session.formattedBuyIn(),
            avgBuyIn = if (shouldHideBalance) "*****" else session.formattedAvgBuyIn(),
            profit = if (shouldHideBalance) "*****" else session.formattedBalance(),
            roi = session.formattedRoi(),
          )
        }
      }
      .onEach { sessions ->
        _componentState.value = ComponentState.Success(sessions)
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(GrindsInteraction.OnGrindEvent::class)
  private suspend fun onTournamentEventInteraction(interaction: GrindsInteraction.OnGrindEvent) {
    when (interaction.event) {
      is GrindAdapterConsumerEvent.SessionClicked -> {
        analytics.onSessionViewed(interaction.event.state.title)
        sendCommand(GrindsCommand.NavigateToSessionDetails(interaction.event.state.session))
      }
    }
  }

  @OnInteraction(GrindsInteraction.NewSessionClicked::class)
  private suspend fun onNewSessionClicked() {
    analytics.onCreateSessionClicked()
    sendCommand(GrindsCommand.NavigateToNewSession)
  }

}