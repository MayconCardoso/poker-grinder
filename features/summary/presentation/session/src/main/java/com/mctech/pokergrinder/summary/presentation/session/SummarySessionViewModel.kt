package com.mctech.pokergrinder.summary.presentation.session

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.summary.domain.entities.SessionSummary
import com.mctech.pokergrinder.summary.domain.usecases.ObserveSessionSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SummarySessionViewModel @Inject constructor(
  private val observeSessionSummaryUseCase: ObserveSessionSummaryUseCase,
) : BaseViewModel() {

  private val _sessionState by lazy {
    MutableStateFlow<ComponentState<SessionSummary>>(ComponentState.Loading.FromEmpty)
  }
  val sessionState: StateFlow<ComponentState<SessionSummary>> by lazy { _sessionState }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeSessionSummary() }
  }

  private fun observeSessionSummary() {
    observeSessionSummaryUseCase()
      .onEach { summary ->
        _sessionState.value = ComponentState.Success(summary)
      }
      .launchIn(viewModelScope)
  }

}