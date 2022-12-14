package com.mctech.architecture_testing

import com.mctech.architecture_testing.rules.CoroutinesMainTestRule
import org.junit.Rule

abstract class BaseViewModelTest {
  @get:Rule
  val coroutinesTestRule = CoroutinesMainTestRule()
}
