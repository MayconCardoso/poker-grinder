package com.mctech.pokergrinder.tournament.presentation.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mctech.pokergrinder.architecture.extensions.avoidFrozenFrames
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.tournament.presentation.list.databinding.FragmentTournamentsBinding
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListCallback
import com.mctech.pokergrinder.tournament.presentation.navigation.TournamentNavigation
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
public class TournamentsFragment : Fragment(R.layout.fragment_tournaments), TournamentListCallback {

  // region Variables

  /**
   * Tournaments Ui Binding
   */
  private val binding by viewBinding(FragmentTournamentsBinding::bind)

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: TournamentNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    avoidFrozenFrames {
      setupListeners()
    }
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.newTournament.setOnClickListener {
      navigateToEditor(tournament = null)
    }
  }

  override fun onTournamentClicked(tournament: Tournament) {
    navigateToEditor(tournament)
  }

  // endregion

  // region Commands

  private fun navigateToEditor(tournament: Tournament?) {
    navigation.goToTournament(tournament)
  }

  // endregion
}