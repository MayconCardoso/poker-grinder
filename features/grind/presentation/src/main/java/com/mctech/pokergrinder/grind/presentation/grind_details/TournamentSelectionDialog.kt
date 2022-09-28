package com.mctech.pokergrinder.grind.presentation.grind_details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.presentation.R
import com.mctech.pokergrinder.grind.presentation.databinding.FragmentTournamentSelectionBinding
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsAdapter
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumer
import com.mctech.pokergrinder.grind.presentation.grind_details.adapter.GrindDetailsConsumerEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TournamentSelectionDialog : DialogFragment(R.layout.fragment_tournament_selection) {

  // region Variables

  /**
   * New Grind View Model
   */
  private val viewModel by viewModels<GrindDetailsViewModel>(
    ownerProducer = { requireParentFragment() }
  )

  /**
   * New Grind Ui Binding
   */
  private val binding by viewBinding(FragmentTournamentSelectionBinding::bind)

  /**
   * Tournaments adapter event consumer.
   */
  private val tournamentAdapterConsumer by lazy {
    object : GrindDetailsConsumer {
      override fun consume(event: GrindDetailsConsumerEvent) {
        viewModel.interact(GrindDetailsInteraction.OnTournamentEvent(event))
        dismiss()
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    GrindDetailsAdapter(eventConsumer = tournamentAdapterConsumer)
  }

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Setup List
    setupTournamentList(
      // TODO fix later. I don't wanna invest a lot of time here now given this feature is important to me.
      (viewModel.commandObservable.value as GrindDetailsCommand.ShowTournamentSelection).options
    )
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return Dialog(
      requireActivity(),
      com.mctech.pokergrinder.design.R.style.Theme_PokerGrinder_FullScreen,
    )
  }

  // endregion

  // region UI Manipulation

  private fun setupTournamentList(tournaments: List<SessionTournament>) {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.tournaments.isVisible = true
    binding.tournaments.adapter = tournamentAdapter
    tournamentAdapter.submitList(tournaments)
  }

  // endregion
}