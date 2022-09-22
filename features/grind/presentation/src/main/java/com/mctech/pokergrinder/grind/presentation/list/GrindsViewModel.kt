package com.mctech.pokergrinder.grind.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveAllGrindsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GrindsViewModel @Inject constructor(
  private val observeAllGrindsUseCase: ObserveAllGrindsUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<List<Session>>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<List<Session>>> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeAllGrindsUseCase()
      .onEach { sessions ->
        _componentState.value = ComponentState.Success(sessions)
      }
      .launchIn(viewModelScope)
  }

}