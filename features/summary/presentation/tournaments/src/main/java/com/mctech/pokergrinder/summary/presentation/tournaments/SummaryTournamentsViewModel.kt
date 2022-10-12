package com.mctech.pokergrinder.summary.presentation.tournaments

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.domain.usecases.ObserveTournamentSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class SummaryTournamentsViewModel @Inject constructor(
  private val observeTournamentSummaryUseCase: ObserveTournamentSummaryUseCase,
) : BaseViewModel() {

  private val _state by lazy {
    MutableStateFlow<ComponentState<List<TournamentSummary>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<TournamentSummary>>> by lazy { _state }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeInvestmentSummary() }
  }

  private fun observeInvestmentSummary() {
    observeTournamentSummaryUseCase()
      .onEach { tournaments ->
        _state.value = ComponentState.Success(tournaments)
      }
      .launchIn(viewModelScope)
  }

}