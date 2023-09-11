package com.mctech.pokergrinder.bankroll.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.bankroll.presentation.list.composables.BankrollTransactionComponent
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.design.compose.PokerGrinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatementFragment : Fragment() {

  // region Variables

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: BankrollNavigation

  // endregion

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        BankrollTransactionComponent(navigation = navigation)
      }
    }
  }

}