package com.mctech.architecture_testing.threading

import com.mctech.pokergrinder.threading.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
object TestCoroutineDispatcher : CoroutineDispatchers {
  override val io
    get() = UnconfinedTestDispatcher()
  override val default: CoroutineDispatcher
    get() = UnconfinedTestDispatcher()
  override val main: CoroutineDispatcher
    get() = UnconfinedTestDispatcher()
  override val immediate: CoroutineDispatcher
    get() = UnconfinedTestDispatcher()
}
