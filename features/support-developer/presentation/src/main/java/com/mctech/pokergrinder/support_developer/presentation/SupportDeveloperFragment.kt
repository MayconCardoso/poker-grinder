package com.mctech.pokergrinder.support_developer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.support_developer.domain.SupportDeveloperAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SupportDeveloperFragment : Fragment() {

  /**
   * Feature analytics
   */
  @Inject
  lateinit var analytics: SupportDeveloperAnalytics

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        SupportDeveloperComponent()
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
      analytics.onSupportDeveloperViewed()
    }
  }
}