package com.mctech.pokergrinder.support_developer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.design.compose.PokerGrinder

class SupportDeveloperFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        SupportDeveloperComponent()
      }
    }
  }
}