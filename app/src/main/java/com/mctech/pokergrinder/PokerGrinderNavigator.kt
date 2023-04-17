package com.mctech.pokergrinder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.mctech.pokergrinder.bankroll.presentation.navigation.BankrollNavigation
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind_gameplay.presentation.creation.RegisterFlipFragment
import com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation
import com.mctech.pokergrinder.grind.presentation.pager_container.GrindDetailContainerFragment
import com.mctech.pokergrinder.grind_tournament.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind_tournament.presentation.creation.RegisterTournamentFragment
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangePosition
import com.mctech.pokergrinder.ranges.presentation.navigation.RangeNavigation
import com.mctech.pokergrinder.ranges.presentation.viewer.RangeViewerDialog
import com.mctech.pokergrinder.ranges.presentation.viewer.RangeViewerFragment
import com.mctech.pokergrinder.ranges_practice.presentation.navigation.RangePracticeNavigation
import com.mctech.pokergrinder.summary.domain.entities.TournamentSummary
import com.mctech.pokergrinder.summary.presentation.navigation.SummaryNavigation
import com.mctech.pokergrinder.summary.presentation.tournaments.details.SummaryTournamentDetailsFragment
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import com.mctech.pokergrinder.tournament.presentation.creation.NewTournamentFragment
import com.mctech.pokergrinder.tournament.domain.entities.Tournament

class PokerGrinderNavigator :
  GrindNavigation,
  RangeNavigation,
  RangePracticeNavigation,
  SummaryNavigation,
  BankrollNavigation,
  TournamentNavigation {

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

  // endregion

  // region Global navigation

  override fun navigateBack() {
    navController?.navigateUp()
  }

  override fun goToSettings() {
    navController?.navigate(R.id.settings_fragment)
  }

  override fun goToSupportDeveloper() {
    navController?.navigate(R.id.support_developer)
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
        putSerializable(RegisterFlipFragment.SESSION_PARAM, session)
      },
    )
  }

  // endregion

  // region Summary

  override fun goToTournamentSummaryDetail(tournamentSummary: TournamentSummary) {
    navController?.navigate(
      R.id.action_summary_fragment_to_summary_tournament_details_fragment,
      Bundle().apply {
        putSerializable(SummaryTournamentDetailsFragment.TOURNAMENT_PARAM, tournamentSummary)
      },
    )
  }

  // endregion


  // region Ranges

  override fun goToRangeDetails(range: Range) {
    navController?.navigate(
      R.id.action_ranges_fragment_to_ranges_viewer_fragment,
      Bundle().apply {
        putSerializable(RangeViewerFragment.RANGE_PARAM, range)
      },
    )
  }

  // endregion


  // region Ranges Practice

  override fun goToRangePracticeTrainer() {
    navController?.navigate(
      R.id.action_range_practice_fragment_to_range_practice_training_fragment,
    )
  }

  override fun goToRangePracticeFilter() {
    navController?.navigate(
      R.id.action_range_practice_training_fragment_to_range_practice_filter_fragment,
    )
  }

  override fun goToRangeViewer(range: RangePosition) {
    navController?.navigate(
      R.id.action_range_practice_training_fragment_to_rangeViewerDialog,
      Bundle().apply {
        putSerializable(RangeViewerDialog.RANGE_POSITION_PARAM, range)
      },
    )
  }

  // endregion

  fun interface Callback {
    fun onDestinationChanged(destination: NavDestination)
  }
}