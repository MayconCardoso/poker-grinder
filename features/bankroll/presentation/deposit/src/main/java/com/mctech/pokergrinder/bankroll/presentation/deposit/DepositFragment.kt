package com.mctech.pokergrinder.bankroll.presentation.deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.bankroll.presentation.deposit.composables.DepositUi
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.design.compose.PokerGrinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DepositFragment : Fragment() {

  // region Variables
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
        DepositComponent(navigation = navigation)
      }
    }
  }

  @Composable
  fun DepositComponent(navigation: BankrollNavigation) {
    // Gets view model
    val viewModel = hiltViewModel<DepositViewModel>()

    // Observe commands
    val command = viewModel.commandObservable.observeAsState().value
    if (command == DepositCommand.CloseScreen) {
      navigation.navigateBack()
      return
    }

    // Draw component
    DepositUi { userInteraction ->
      viewModel.interact(userInteraction)
    }
  }

  // endregion

}