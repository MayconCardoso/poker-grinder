package com.mctech.pokergrinder.grind.presentation.current_grind

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.usecase.ObserveCurrentGrindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
public class CurrentGrindViewModel @Inject constructor(
  private val observeCurrentGrindUseCase: ObserveCurrentGrindUseCase,
) : BaseViewModel() {

  private val _componentState by lazy { MutableStateFlow<Session?>(null) }
  public val componentState: StateFlow<Session?> by lazy { _componentState }

  override suspend fun initializeComponents() {
    observeCurrentGrindUseCase()
      .onEach { session ->
        _componentState.value = session
      }
      .launchIn(viewModelScope)
  }

}