package com.mctech.pokergrinder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.pager_container.GrindDetailContainerFragment
import com.mctech.pokergrinder.grind.presentation.tournamnet_creation.RegisterTournamentFragment
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import com.mctech.pokergrinder.tournament.presentation.creation.NewTournamentFragment
import com.mctech.pokergrinder.tournament.domain.entities.Tournament

class PokerGrinderNavigator : TournamentNavigation, BankrollNavigation, GrindNavigation {

  // region Navigator setup

  /**
   * Navigator controller.
   */
  private var navController: NavController? = null

  /**
   * Holds the navigator callback used to setup toolbar and bottom navigation.
   */
  private val destinationChangedCallback by lazy {
    NavController.OnDestinationChangedListener { _, destination, _ ->
      navigatorCallback?.onDestinationChanged(destination)
    }
  }

  /**
   * Navigation callback.
   */
  var navigatorCallback: Callback? = null

  /**
   * Used to setup controller.
   */
  fun bindNavController(navController: NavController) {
    this.navController = navController
    this.navController?.addOnDestinationChangedListener(destinationChangedCallback)
  }

  /**
   * Used to release controller and avoid memory leak.
   */
  fun unbind() {
    navController?.removeOnDestinationChangedListener(destinationChangedCallback)
    navController = null
  }

  override fun navigateBack() {
    navController?.navigateUp()
  }

  override fun goToSettings() {
    navController?.navigate(R.id.settings_fragment)
  }
  // endregion

  // region Tournament navigation

  override fun goToTournament(tournament: Tournament?) {
    navController?.navigate(
      R.id.action_tournament_fragment_to_tournament_register,
      Bundle().apply {
        putSerializable(NewTournamentFragment.TOURNAMENT_PARAM, tournament)
      }
    )
  }

  // endregion

  // region Bankroll navigation

  override fun goToBankrollDeposit() {
    navController?.navigate(R.id.action_statement_fragment_to_statement_register)
  }

  override fun goToBankrollWithdraw() {
    navController?.navigate(R.id.action_statement_fragment_to_statement_withdraw)
  }

  // endregion

  // region Grind navigation

  override fun goToNewSession() {
    navController?.navigate(R.id.action_session_fragment_to_session_register)
  }

  override fun goToSessionDetails(session: Session) {
    navController?.navigate(
      R.id.action_session_fragment_to_session_details,
      Bundle().apply {
        putSerializable(GrindDetailContainerFragment.SESSION_PARAM, session)
      },
    )
  }

  override fun goToSessionTournament(session: Session, sessionTournament: SessionTournament?) {
    navController?.navigate(
      R.id.action_session_fragment_to_session_details_tournament,
      Bundle().apply {
        putSerializable(RegisterTournamentFragment.SESSION_PARAM, session)
        putSerializable(RegisterTournamentFragment.TOURNAMENT_PARAM, sessionTournament)
      },
    )
  }

  override fun goToSessionTournamentGameplay(session: Session) {
    navController?.navigate(
      R.id.action_session_details_to_session_details_tournament_flip,
      Bundle().apply {
        putSerializable(com.mctech.pokergrinder.grind.presentation.flip_creation.RegisterFlipFragment.SESSION_PARAM, session)
      },
    )
  }

  // endregion

  interface Callback {
    fun onDestinationChanged(destination: NavDestination)
  }
}