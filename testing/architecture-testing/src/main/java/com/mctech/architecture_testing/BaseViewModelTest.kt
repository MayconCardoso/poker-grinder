package com.mctech.architecture_testing

import com.mctech.architecture_testing.rules.CoroutinesMainTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {
  @get:Rule
  val coroutinesTestRule = CoroutinesMainTestRule()
}
