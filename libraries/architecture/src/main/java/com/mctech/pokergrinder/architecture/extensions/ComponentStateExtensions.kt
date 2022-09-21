package com.mctech.pokergrinder.architecture.extensions

import com.mctech.pokergrinder.architecture.ComponentState
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Emits loading state.
 */
inline fun <reified T> MutableStateFlow<ComponentState<T>>.emitLoading() {
  if (T::class.isInstance(List::class)) {
    this.value = (this.value as ComponentState<List<*>>).emitLoadingListState()
  } else {
    this.value = ComponentState.Loading.FromEmpty
  }
}

/**
 * Emits success state.
 */
fun <T> MutableStateFlow<ComponentState<T>>.emitSuccess(successValue: T) {
  this.value = ComponentState.Success(successValue)
}

/**
 * Emits error state.
 */
fun <T> MutableStateFlow<ComponentState<T>>.emitError(exception: Throwable) {
  val currentState = this.value

  this.value = when (currentState) {
    is ComponentState.Success -> {
      ComponentState.Error(exception, currentState.result)
    }
    is ComponentState.Error -> {
      ComponentState.Error(exception, currentState.lastData)
    }
    else -> {
      ComponentState.Error(exception, null)
    }
  }
}

/**
 * Resolve the LoadingState according the list data.
 * If there are items, we load from state, otherwise we load from empty.
 */
fun ComponentState<List<*>>.emitLoadingListState(): ComponentState.Loading {
  return when (this) {
    is ComponentState.Success -> {
      if (result.isEmpty()) {
        ComponentState.Loading.FromEmpty
      } else {
        ComponentState.Loading.FromState(this)
      }
    }
    else -> {
      ComponentState.Loading.FromEmpty
    }
  }
}
