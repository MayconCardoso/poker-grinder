package com.mctech.pokergrinder.grind.presentation.flip_creation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.pokergrinder.architecture.ViewCommand
import com.mctech.pokergrinder.architecture.extensions.bindCommand
import com.mctech.pokergrinder.architecture.extensions.bindState
import com.mctech.pokergrinder.architecture.extensions.dp
import com.mctech.pokergrinder.architecture.extensions.viewBinding
import com.mctech.pokergrinder.architecture.utility.SimpleSpaceItemDecoration
import com.mctech.pokergrinder.deck.presentation.card_picker.CardPickerCommand
import com.mctech.pokergrinder.deck.presentation.card_picker.CardPickerInteraction
import com.mctech.pokergrinder.deck.presentation.card_picker.CardPickerViewModel
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.flip_creation.adapter.RegisterFlipTournamentAdapter
import com.mctech.pokergrinder.grind.presentation.flip_creation.adapter.RegisterFlipTournamentConsumer
import com.mctech.pokergrinder.grind.presentation.flip_creation.adapter.RegisterFlipTournamentConsumerEvent
import com.mctech.pokergrinder.grind.presentation.flip_creation.databinding.FragmentRegisterTournamentFlipBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class RegisterFlipFragment : Fragment(R.layout.fragment_register_tournament_flip) {

  // region Variables

  /**
   * Tournament View Model
   */
  private val viewModel by viewModels<RegisterFlipViewModel>()

  /**
   * Card picker View Model
   */
  private val cardPickerViewModel by viewModels<CardPickerViewModel>()

  /**
   * Tournament Ui Binding
   */
  private val binding by viewBinding(FragmentRegisterTournamentFlipBinding::bind)

  /**
   * Tournaments adapter event consumer.
   */
  private val tournamentAdapterConsumer by lazy {
    object : RegisterFlipTournamentConsumer {
      override fun consume(event: RegisterFlipTournamentConsumerEvent) {
        viewModel.interact(RegisterFlipInteraction.OnTournamentEvent(event))
      }
    }
  }

  /**
   * Tournaments adapter.
   */
  private val tournamentAdapter by lazy {
    RegisterFlipTournamentAdapter(eventConsumer = tournamentAdapterConsumer)
  }

  /**
   * Handles the back pressed event.
   */
  private val backPressedCallback by lazy { obtainOnBackPressedCallback() }

  /**
   * Feature navigation
   */
  @Inject
  public lateinit var navigation: com.mctech.pokergrinder.grind.presentation.navigation.GrindNavigation

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // Gets tournament
    val session = arguments?.getSerializable(SESSION_PARAM) as Session
    viewModel.interact(RegisterFlipInteraction.ScreenFirstOpen(session))

    // Setup Listeners
    setupListeners()

    // Setup Listeners
    setupTournamentList()

    // Observes state.
    bindState(viewModel.componentState, ::rendersState)

    // Observes commands
    bindCommand(viewModel, ::consumeCommand)

    // Observe card picker command
    bindCommand(cardPickerViewModel, ::consumeCommand)
  }

  // endregion

  // region State Manipulation

  private fun rendersState(state: RegisterFlipState) {
    // Disable back navigation if needed.
    backPressedCallback.isEnabled = state.currentFlow.isNotOneOf(RegisterFlipFlow.TOURNAMENT_PICKER)

    // Change component texts.
    binding.instruction.setText(state.message)

    // Setup tournaments
    tournamentAdapter.submitList(state.tournaments)

    // Setup disabled cards
    cardPickerViewModel.interact(CardPickerInteraction.DisableCards(state.disableCards))

    // Setup containers
    renderContainer(state)
  }

  private fun renderContainer(state: RegisterFlipState) {
    binding.heroWon.isVisible = state.currentFlow.isOneOf(RegisterFlipFlow.WHO_WON)
    binding.villainWon.isVisible = state.currentFlow.isOneOf(RegisterFlipFlow.WHO_WON)
    binding.instruction.isVisible = state.currentFlow.isNotOneOf(RegisterFlipFlow.LOADING)

    binding.progress.isVisible = state.currentFlow.isOneOf(RegisterFlipFlow.LOADING)

    binding.cardPicker.isVisible = state.currentFlow.isOneOf(
      RegisterFlipFlow.HERO_CARD_PICKER,
      RegisterFlipFlow.VILLAIN_CARD_PICKER,
      RegisterFlipFlow.BOARD_PICKER,
    )

    binding.tournaments.isVisible = state.currentFlow.isOneOf(RegisterFlipFlow.TOURNAMENT_PICKER)
  }

  // endregion

  // region Component Setup

  private fun setupListeners() {
    binding.heroWon.setOnClickListener {
      viewModel.interact(RegisterFlipInteraction.HeroWonFlip)
    }
    binding.villainWon.setOnClickListener {
      viewModel.interact(RegisterFlipInteraction.VillainWonFlip)
    }

    // Observes back pressed.
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
  }

  private fun setupTournamentList() {
    binding.tournaments.addItemDecoration(SimpleSpaceItemDecoration(bottomOffset = 12.dp()))
    binding.tournaments.adapter = tournamentAdapter
  }

  private fun obtainOnBackPressedCallback(): OnBackPressedCallback {
    return object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        viewModel.interact(RegisterFlipInteraction.OnBackPressed)
      }
    }
  }

  // endregion

  // region Commands

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is RegisterFlipCommand.CloseScreen -> {
        navigation.navigateBack()
      }
      is CardPickerCommand.CardSelected -> {
        viewModel.interact(RegisterFlipInteraction.CardSelected(command.card))
      }
      is CardPickerCommand.CardUnselected -> {
        viewModel.interact(RegisterFlipInteraction.CardUnselected(command.card))
      }
    }
  }

  // endregion

  // region Builder

  public companion object {
    public const val SESSION_PARAM: String = "SESSION_PARAM"
  }

  // endregion
}