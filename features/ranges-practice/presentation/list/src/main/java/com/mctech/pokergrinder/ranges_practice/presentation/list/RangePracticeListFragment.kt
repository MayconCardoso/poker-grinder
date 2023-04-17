package com.mctech.pokergrinder.ranges_practice.presentation.list

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
class RangePracticeListFragment : Fragment() {

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: RangePracticeNavigation

  /**
   * Feature View Model
   */
  private val viewModel by viewModels<RangePracticeListViewModel>()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        RangePracticeComponent(viewModel = viewModel)
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bindCommand(viewModel, ::consumeCommand)
  }

  private fun consumeCommand(command: ViewCommand) {
    when(command) {
      is RangePracticeListCommand.GoToPracticing -> navigation.goToRangePracticeTrainer()
    }
  }

}