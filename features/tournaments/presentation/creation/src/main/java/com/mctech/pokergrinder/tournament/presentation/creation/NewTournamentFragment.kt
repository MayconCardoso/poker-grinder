package com.mctech.pokergrinder.tournament.presentation.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.extensions.deserialize
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.presentation.creation.composables.NewTournamentUi
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewTournamentFragment : Fragment() {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<NewTournamentViewModel>()

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
        NewTournamentComponent(
          viewModel = viewModel,
          navigation = navigation,
        )
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Gets tournament
    val tournament = arguments?.deserialize<Tournament>(TOURNAMENT_PARAM)
    viewModel.interact(NewTournamentInteraction.ScreenFirstOpen(tournament))
  }

  @Composable
  private fun NewTournamentComponent(
    navigation: TournamentNavigation,
    viewModel: NewTournamentViewModel = hiltViewModel(),
  ) {
    // Gets view model
    val tournamentState = viewModel.componentState.collectAsState().value

    // Observe commands
    val command = viewModel.commandObservable.observeAsState().value
    if (command == NewTournamentCommand.CloseScreen) {
      navigation.navigateBack()
      return
    }

    // Draw component
    NewTournamentUi(tournamentState = tournamentState) { userInteraction ->
      viewModel.interact(userInteraction)
    }
  }

  // endregion

  // region Builder

  companion object {
    const val TOURNAMENT_PARAM: String = "TOURNAMENT_PARAM"
  }

  // endregion

}