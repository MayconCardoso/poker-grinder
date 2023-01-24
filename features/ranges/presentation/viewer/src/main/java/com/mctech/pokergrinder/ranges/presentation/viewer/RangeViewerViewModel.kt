package com.mctech.pokergrinder.ranges.presentation.viewer

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveRangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class RangeViewerViewModel @Inject constructor(
  private val observeRangeUseCase: ObserveRangeUseCase,
) : BaseViewModel() {

  private val _componentState by lazy {
    MutableStateFlow<ComponentState<Range>>(ComponentState.Loading.FromEmpty)
  }
  val componentState: StateFlow<ComponentState<Range>> by lazy { _componentState }

  @OnInteraction(RangeViewerInteraction.OnScreenFirstOpened::class)
  private fun onScreenFirstOpenInteraction(
    interaction: RangeViewerInteraction.OnScreenFirstOpened,
  ) {
    observeRangeUseCase(interaction.range)
      .onEach { range ->
        _componentState.value = ComponentState.Success(range)
      }
      .launchIn(viewModelScope)
  }

}