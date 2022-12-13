package com.mctech.architecture_testing.rules

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesMainTestRule constructor(
  private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : InstantTaskExecutorRule() {

  override fun starting(description: Description) {
    super.starting(description)
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description) {
    super.finished(description)
    Dispatchers.resetMain()
    testDispatcher.cleanupTestCoroutines()
  }
}
