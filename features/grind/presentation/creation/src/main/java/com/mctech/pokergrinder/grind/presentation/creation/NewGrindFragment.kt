package com.mctech.pokergrinder.grind.presentation.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewGrindFragment : Fragment() {

  // region Variables

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: GrindNavigation

  // endregion

  // region Lifecycle

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        NewGrindComponent(navigation = navigation)
      }
    }
  }
  // endregion

}