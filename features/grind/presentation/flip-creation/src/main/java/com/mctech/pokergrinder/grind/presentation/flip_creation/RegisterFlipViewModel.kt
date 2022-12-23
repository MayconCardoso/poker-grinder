package com.mctech.pokergrinder.grind.presentation.flip_creation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.domain.entities.SessionTournament
import com.mctech.pokergrinder.grind.domain.usecase.ObserveGrindTournamentUseCase
import com.mctech.pokergrinder.grind.domain.usecase.RegisterTournamentFlipUseCase
import com.mctech.pokergrinder.grind.presentation.flip_creation.adapter.RegisterFlipTournamentConsumerEvent
import com.mctech.pokergrinder.localization.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class RegisterFlipViewModel @Inject constructor(
  private val observeGrindTournamentUseCase: ObserveGrindTournamentUseCase,
  private val registerTournamentFlipUseCase: RegisterTournamentFlipUseCase,
) : BaseViewModel() {

  // region Variables

  /**
   * Holds the grind session
   */
  @VisibleForTesting
  var session: Session? = null

  /**
   * Holds the selected tournament
   */
  @VisibleForTesting
  var selectedTournament: SessionTournament? = null

  /**
   * Holds the hero cards.
   */
  @VisibleForTesting
  var heroCards = mutableListOf<Card>()

  /**
   * Holds the villain cards.
   */
  @VisibleForTesting
  var villainCards = mutableListOf<Card>()

  /**
   * Holds the board cards.
   */
  @VisibleForTesting
  var boardCards = mutableListOf<Card>()

  /**
   * Holds the component current rendered state.
   */
  private val _componentState by lazy { MutableStateFlow(RegisterFlipState()) }
  val componentState: StateFlow<RegisterFlipState> by lazy { _componentState }

  // endregion

  // region Interactions

  @OnInteraction(RegisterFlipInteraction.ScreenFirstOpen::class)
  private fun onScreenOpened(interaction: RegisterFlipInteraction.ScreenFirstOpen) {
    // Holds session
    this.session = interaction.session

    // Start observing tournaments
    observeGrindTournamentUseCase(interaction.session.id)
      .map { it -> it.distinctBy { it.title } }
      .onEach { tournaments ->
        _componentState.value = RegisterFlipState(
          tournaments = tournaments,
          currentFlow = RegisterFlipFlow.TOURNAMENT_PICKER,
        )
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(RegisterFlipInteraction.TournamentSelected::class)
  private fun onTournamentEvent(interaction: RegisterFlipInteraction.TournamentSelected) {
    // Holds the selected tournament
    this.selectedTournament = interaction.tournament

    // Changes the flow
    _componentState.value = _componentState.value.copy(
      message = R.string.select_hero_cards,
      currentFlow = RegisterFlipFlow.HERO_CARD_PICKER,
    )
  }

  @OnInteraction(RegisterFlipInteraction.CardSelected::class)
  private fun onCardSelected(interaction: RegisterFlipInteraction.CardSelected) {
    // Handle card selection.
    when (componentState.value.currentFlow) {
      RegisterFlipFlow.BOARD_PICKER -> handleBoardCardSelected(interaction.card)
      RegisterFlipFlow.HERO_CARD_PICKER -> handleHeroCardSelected(interaction.card)
      RegisterFlipFlow.VILLAIN_CARD_PICKER -> handleVillainCardSelected(interaction.card)
      else -> Unit
    }

    // Emit new selected cards state
    emitSelectedCardState()
  }

  @OnInteraction(RegisterFlipInteraction.CardUnselected::class)
  private fun onCardUnselected(interaction: RegisterFlipInteraction.CardUnselected) {
    // Handle card selection.
    when (componentState.value.currentFlow) {
      RegisterFlipFlow.BOARD_PICKER -> handleBoardCardUnselected(interaction.card)
      RegisterFlipFlow.HERO_CARD_PICKER -> handleHeroCardUnselected(interaction.card)
      RegisterFlipFlow.VILLAIN_CARD_PICKER -> handleVillainCardUnselected(interaction.card)
      else -> Unit
    }

    // Emit new selected cards state
    emitSelectedCardState()
  }

  @OnInteraction(RegisterFlipInteraction.OnBackPressed::class)
  private fun onBackPressed() {
    // Gets new flow
    val newFlow = when (componentState.value.currentFlow) {
      RegisterFlipFlow.HERO_CARD_PICKER -> RegisterFlipFlow.TOURNAMENT_PICKER
      RegisterFlipFlow.VILLAIN_CARD_PICKER -> RegisterFlipFlow.HERO_CARD_PICKER
      RegisterFlipFlow.BOARD_PICKER -> RegisterFlipFlow.VILLAIN_CARD_PICKER
      RegisterFlipFlow.WHO_WON -> RegisterFlipFlow.BOARD_PICKER
      else -> RegisterFlipFlow.TOURNAMENT_PICKER
    }

    // Gets new message
    val newMessage = when (componentState.value.currentFlow) {
      RegisterFlipFlow.HERO_CARD_PICKER -> R.string.select_the_tournament
      RegisterFlipFlow.VILLAIN_CARD_PICKER -> R.string.select_hero_cards
      RegisterFlipFlow.BOARD_PICKER -> R.string.select_villain_cards
      RegisterFlipFlow.WHO_WON -> R.string.select_board_cards
      else -> R.string.select_the_tournament
    }

    // Emits new state
    _componentState.value = componentState.value.copy(
      message = newMessage,
      currentFlow = newFlow,
    )
  }

  @OnInteraction(RegisterFlipInteraction.HeroWonFlip::class)
  private suspend fun onHeroWonClicked() {
    // Save flip
    registerTournamentFlipUseCase(
      session = requireNotNull(session),
      title = selectedTournament?.title.orEmpty(),
      heroCards = heroCards,
      villainCards = villainCards,
      boardCards = boardCards,
      heroWon = true,
    )

    // Close screen
    sendCommand(RegisterFlipCommand.CloseScreen)
  }

  @OnInteraction(RegisterFlipInteraction.VillainWonFlip::class)
  private suspend fun onVillainWonClicked() {
    // Save flip
    registerTournamentFlipUseCase(
      session = requireNotNull(session),
      title = selectedTournament?.title.orEmpty(),
      heroCards = heroCards,
      villainCards = villainCards,
      boardCards = boardCards,
      heroWon = false,
    )

    // Close screen
    sendCommand(RegisterFlipCommand.CloseScreen)
  }

  // endregion

  // region Card Selected Flow

  private fun handleBoardCardSelected(card: Card) {
    // Adds new card
    boardCards.add(card)

    // All board cards have been selected
    if (boardCards.size == 5) {
      _componentState.value = _componentState.value.copy(
        message = R.string.select_the_winner,
        currentFlow = RegisterFlipFlow.WHO_WON,
      )
    }
  }

  private fun handleHeroCardSelected(card: Card) {
    // Adds new card
    heroCards.add(card)

    // Checks if all villain cards have been selected.
    // If so, emit new state to change user flow.
    if (heroCards.size == 2) {
      _componentState.value = _componentState.value.copy(
        message = R.string.select_villain_cards,
        currentFlow = RegisterFlipFlow.VILLAIN_CARD_PICKER,
      )
    }
  }

  private fun handleVillainCardSelected(card: Card) {
    // Adds new card
    villainCards.add(card)

    // Checks if all hero cards have been selected.
    // If so, emit new state to change user flow.
    if (villainCards.size == 2) {
      _componentState.value = _componentState.value.copy(
        message = R.string.select_board_cards,
        currentFlow = RegisterFlipFlow.BOARD_PICKER,
      )
    }
  }

  // endregion

  // region Card Unselected Flow

  private fun handleBoardCardUnselected(card: Card) {
    boardCards.remove(card)
  }

  private fun handleHeroCardUnselected(card: Card) {
    heroCards.remove(card)
  }

  private fun handleVillainCardUnselected(card: Card) {
    villainCards.remove(card)
  }

  // endregion

  // region Common functions

  private fun emitSelectedCardState() {
    // Get all selected cards.
    val selectedCards = buildList {
      addAll(heroCards)
      addAll(boardCards)
      addAll(villainCards)
    }

    // Emit new state with disabled cards.
    _componentState.value = componentState.value.copy(
      disableCards = selectedCards
    )
  }

  // endregion
}