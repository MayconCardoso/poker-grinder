package com.mctech.pokergrinder.tournament.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TournamentsFragment : Fragment() {

  // region Variables

  /**
   * Feature navigation
   */
  @Inject
  lateinit var navigation: TournamentNavigation

  // endregion

  // region Lifecycle

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(inflater.context).apply {
    setContent {
      PokerGrinder.PokerGrinderTheme {
        TournamentsComponent(
          callback = { tournament ->
            navigation.goToTournament(tournament)
          }
        )
      }
    }
  }

  // endregion

}