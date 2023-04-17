package com.mctech.pokergrinder.ranges_practice.presentation.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RangePracticeFragment : Fragment() {

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<RangePracticeViewModel>()

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: RangePracticeNavigation

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        RangePracticeComponent(viewModel = viewModel)
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Observes commands
    bindCommand(viewModel, ::consumeCommand)
  }

  private fun consumeCommand(command: ViewCommand) {
    when(command) {
      is RangePracticeCommand.OpenFilterScreen -> {
        navigation.goToRangePracticeFilter()
      }
      is RangePracticeCommand.OpenRangeScreen -> {
        navigation.goToRangeViewer(command.range)
      }
    }
  }

}