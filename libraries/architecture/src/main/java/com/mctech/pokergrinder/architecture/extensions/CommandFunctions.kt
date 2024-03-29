package com.mctech.pokergrinder.architecture.extensions

import androidx.lifecycle.LifecycleOwner
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.utility.SingleLiveEvent

/**
 * It is called when you wanna observe a single event and then stop to observing it.
 */
internal fun autoDisposeCommandObserver(
  lifecycle: LifecycleOwner,
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  val key = lifecycle.toString()
  val commandObservable = ((viewModel.commandObservable) as SingleLiveEvent<ViewCommand>)
  commandObservable.observe(key, lifecycle) {
    block(it)
    commandObservable.removeObserver(key)
  }
}

/**
 * It is called when you wanna observe all commands while the lifecycle owner is activated.
 */
internal fun commandObserver(
  lifecycle: LifecycleOwner,
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  ((viewModel.commandObservable) as SingleLiveEvent<ViewCommand>).observe(
    lifecycle.toString(),
    lifecycle,
  ) {
    block(it)
  }
}
