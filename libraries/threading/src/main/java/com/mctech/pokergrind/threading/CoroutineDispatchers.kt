package com.mctech.pokergrind.threading

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {

  /**
   * Must be used for io executions like database, api calls, etc.
   */
  val io: CoroutineDispatcher

  /**
   * Must be used for computation executions
   */
  val default: CoroutineDispatcher

  /**
   * Must be used for main thread executions
   */
  val main: CoroutineDispatcher

  /**
   * Must be used for immediate main thread executions
   */
  val immediate: CoroutineDispatcher
}

object DefaultCoroutineDispatchers : CoroutineDispatchers {
  override val io = Dispatchers.IO
  override val default = Dispatchers.Default
  override val main = Dispatchers.Main
  override val immediate = Dispatchers.Main.immediate
}
