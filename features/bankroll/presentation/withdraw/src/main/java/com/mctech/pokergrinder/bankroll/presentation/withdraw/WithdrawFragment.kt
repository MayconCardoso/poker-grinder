package com.mctech.pokergrinder.bankroll.presentation.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.bankroll.domain.BankrollAnalytics
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.bankroll.presentation.withdraw.composable.WithdrawUi
import com.mctech.pokergrinder.design.compose.PokerGrinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WithdrawFragment : Fragment() {

  // region Variables

  /**
   * Feature analytics
   */
  @Inject
  lateinit var analytics: BankrollAnalytics

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: BankrollNavigation

  // endregion

  // region Lifecycle
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        WithdrawComponent(navigation = navigation)
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      analytics.onWithdrawScreenViewed()
    }
  }

  @Composable
  fun WithdrawComponent(navigation: BankrollNavigation) {
    // Gets view model
    val viewModel = hiltViewModel<WithdrawViewModel>()

    // Observe commands
    val command = viewModel.commandObservable.observeAsState().value
    if (command == WithdrawCommand.CloseScreen) {
      navigation.navigateBack()
      return
    }

    // Draw component
    WithdrawUi { userInteraction ->
      viewModel.interact(userInteraction)
    }
  }

  // endregion

}