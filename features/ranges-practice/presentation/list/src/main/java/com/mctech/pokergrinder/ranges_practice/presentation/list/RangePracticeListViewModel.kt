package com.mctech.pokergrinder.ranges_practice.presentation.list

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import com.mctech.pokergrinder.ranges_practice.domain.usecases.ObserveRangePracticeResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class RangePracticeListViewModel @Inject constructor(
  private val observeRangePracticeResultUseCase: ObserveRangePracticeResultUseCase,
) : BaseViewModel() {

  private val _state by lazy {
    MutableStateFlow<ComponentState<List<RangePracticeResult>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<RangePracticeResult>>> by lazy {
    _state
  }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeTrainingAnswers() }
  }

  private fun observeTrainingAnswers() {
    observeRangePracticeResultUseCase()
      .onEach { transactions ->
        _state.value = ComponentState.Success(transactions)
      }
      .launchIn(viewModelScope)
  }

}