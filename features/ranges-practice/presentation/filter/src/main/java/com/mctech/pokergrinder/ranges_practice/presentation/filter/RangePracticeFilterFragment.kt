package com.mctech.pokergrinder.ranges_practice.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RangePracticeFilterFragment : Fragment() {

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: RangePracticeNavigation

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        RangePracticeFilterComponent(navigation = navigation)
      }
    }
  }

}