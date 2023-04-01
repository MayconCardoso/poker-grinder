package com.mctech.pokergrinder.tournament.presentation.list_component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.design.compose.PokerGrinder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentListFragment : Fragment() {

  // region Variables

  /**
   * Tournaments View Model
   */
  private val viewModel by viewModels<TournamentListViewModel>(
    ownerProducer = { requireParentFragment() }
  )

  /**
   * Gets the client callback.
   */
  private val clickCallback by lazy {
    (parentFragment as? TournamentListCallback) ?: requireActivity() as TournamentListCallback
  }

  // endregion

  // region Lifecycle

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        TournamentListComponent(viewModel = viewModel, callback = clickCallback)
      }
    }
  }

  // endregion

}