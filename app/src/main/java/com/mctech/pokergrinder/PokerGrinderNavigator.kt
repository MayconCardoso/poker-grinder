package com.mctech.pokergrinder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollNavigation
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.grind_details.GrindDetailsFragment
import com.mctech.pokergrinder.grind.presentation.tournamnet_creation.RegisterTournamentFragment
import com.mctech.pokergrinder.tournament.presentation.TournamentNavigation
import com.mctech.pokergrinder.tournament.presentation.creation.NewTournamentFragment
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

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

  // endregion

  // region Grind navigation

  override fun goToNewSession() {
    navController?.navigate(R.id.action_session_fragment_to_session_register)
  }

  override fun goToSessionDetails(session: Session) {
    navController?.navigate(
      R.id.action_session_fragment_to_session_details,
      Bundle().apply {
        putSerializable(GrindDetailsFragment.SESSION_PARAM, session)
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

  // endregion

  interface Callback {
    fun onDestinationChanged(destination: NavDestination)
  }
}