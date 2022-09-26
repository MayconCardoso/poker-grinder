package com.mctech.pokergrinder.tournaments.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament
import com.mctech.pokergrinder.tournaments.domain.usecase.ObserveTournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class TournamentListViewModel @Inject constructor(
  private val observeTournamentUseCase: ObserveTournamentUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Tournament>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<Tournament>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeTournamentUseCase()
      .onEach { tournaments ->
        _componentState.value = ComponentState.Success(tournaments)
      }
      .launchIn(viewModelScope)
  }

}