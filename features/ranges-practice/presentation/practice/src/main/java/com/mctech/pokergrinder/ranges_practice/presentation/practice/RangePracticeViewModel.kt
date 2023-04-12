package com.mctech.pokergrinder.ranges_practice.presentation.practice

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.deck.domain.Deck
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveAllRangesUseCase
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeQuestion
import com.mctech.pokergrinder.ranges_practice.domain.usecases.ObserveRangePracticeFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class RangePracticeViewModel @Inject constructor(
  private val observeAllRangesUseCase: ObserveAllRangesUseCase,
  private val observeRangePracticeFilterUseCase: ObserveRangePracticeFilterUseCase,
) : BaseViewModel() {

  private var currentRanges = listOf<Range>()
  private var currentRenderedState: RangePracticeState? = null
  private var currentLearningFilter = RangePracticeFilter()

  private val _state by lazy { MutableStateFlow<ComponentState<RangePracticeState>>(ComponentState.Loading.FromEmpty) }
  val state: StateFlow<ComponentState<RangePracticeState>> by lazy { _state }

  override suspend fun initializeComponents() {
    viewModelScope.async { observeRangePracticeFilter() }
  }

  private fun observeRangePracticeFilter() {
    observeRangePracticeFilterUseCase()
      .combine(observeAllRangesUseCase()) { filter, ranges ->
        // Hold data for future usage.
        currentRanges = ranges
        currentLearningFilter = filter

        // Generates a new question
        computesNextQuestion()
      }
      .launchIn(viewModelScope)
  }

  @OnInteraction(RangePracticeInteraction.OnActionClicked::class)
  private suspend fun onActionClicked() {
    // Changes the state
    currentRenderedState = currentRenderedState?.copy(
      infoMessage = R.string.that_is_correct,
      isShowingNextQuestion = true,
      isShowingActionButtons = false,
      isShowingRangeButtonVisible = false,
    )

    // Emits state
    _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
  }

  @OnInteraction(RangePracticeInteraction.OnFoldClicked::class)
  private suspend fun onFoldClicked() {
    // Changes the state
    currentRenderedState = currentRenderedState?.copy(
      infoMessage = R.string.that_is_not_correct,
      isShowingNextQuestion = true,
      isShowingActionButtons = false,
      isShowingRangeButtonVisible = false,
    )

    // Emits state
    _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
  }

  @OnInteraction(RangePracticeInteraction.OnNextQuestionClicked::class)
  private suspend fun onNextQuestionClicked() {
    // Computes next card
    computesNextQuestion()
  }

  private suspend fun computesNextQuestion() {
    // Changes component to loading state.
    _state.value = ComponentState.Loading.FromEmpty

    // Get cards
    val question = RangePracticeQuestion(
      // Gets defined stack on filter or a random one.
      stack = resolveQuestionStack(),
      // Resolve dealt cards
      cards = resolveQuestionCards(),
      // Hardcoded open action for now given the app limitation
      action = RangeAction.OPEN,
      // Gets defined stack on filter or a random one.
      position = resolvePlayerPosition(),
    )

    // Creates state
    currentRenderedState = RangePracticeState(
      question = question,
      infoMessage = null,
      isShowingNextQuestion = false,
      isShowingActionButtons = true,
      isShowingRangeButtonVisible = false,
    )

    // Changes state
    _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
  }

  private fun resolveQuestionStack(): Int {
    return currentLearningFilter.stack ?: currentRanges.map { it.effectiveStack }.random()
  }

  private fun resolveQuestionCards(): String {
    val firstCard = Deck.cards.random().formattedName()
    var secondCard: String
    do {
      secondCard = Deck.cards.random().formattedName()
    } while (secondCard == firstCard)

    return firstCard + secondCard
  }

  private fun resolvePlayerPosition(): RangePlayerPosition {
    return currentLearningFilter.position ?: currentRanges
      .map { it.handPosition }
      .flatten()
      .map { it.position }
      .distinct()
      .random()
  }
}