package com.mctech.pokergrinder.ranges_practice.presentation.practice

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.deck.domain.Deck
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveAllRangesUseCase
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeQuestion
import com.mctech.pokergrinder.ranges_practice.domain.usecases.ObserveRangePracticeFilterUseCase
import com.mctech.pokergrinder.ranges_practice.domain.usecases.SaveRangePracticeAnswerUseCase
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RangePracticeViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeAllRangesUseCase: ObserveAllRangesUseCase,
  private val saveRangePracticeAnswerUseCase: SaveRangePracticeAnswerUseCase,
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
  private suspend fun onActionClicked() = validatesQuestionAnswer(
    shouldHaveAction = true,
  )

  @OnInteraction(RangePracticeInteraction.OnFoldClicked::class)
  private suspend fun onFoldClicked() = validatesQuestionAnswer(
    shouldHaveAction = false,
  )

  @OnInteraction(RangePracticeInteraction.OnNextQuestionClicked::class)
  private suspend fun onNextQuestionClicked() {
    // Computes next card
    computesNextQuestion()
  }

  private suspend fun computesNextQuestion() = viewModelScope.launch {
    // Changes component to loading state.
    _state.value = ComponentState.Loading.FromEmpty

    // Gets defined stack on filter or a random one.
    val stackTask = async { resolveQuestionStack() }
    // Resolve dealt cards
    val cardsTask = async { resolveQuestionCards() }
    // Resolve action
    val actionTask = async { resolveAction() }
    // Resolve applied filters action
    val countOfFilter = async { countOfAppliedFilter() }
    // Gets defined stack on filter or a random one.
    val heroPositionTask = async { resolveHeroPosition() }
    // Gets defined stack on filter or a random one.
    val villainPositionTask = async { resolveVillainPosition() }

    // Await all tasks
    awaitAll(stackTask, cardsTask, heroPositionTask, villainPositionTask, countOfFilter)

    // Get cards
    val question = RangePracticeQuestion(
      // Gets defined stack on filter or a random one.
      stack = stackTask.await(),
      // Resolve dealt cards.
      cards = cardsTask.await(),
      // Gets defined action on filter or a random one.
      action = actionTask.await(),
      // Gets defined hero position at the table.
      heroPosition = heroPositionTask.await(),
      // Gets defined villain position at the table.
      villainPosition = villainPositionTask.await(),
    )

    // Creates state
    currentRenderedState = RangePracticeState(
      question = question,
      infoMessage = null,
      countAppliedFilter = countOfFilter.await(),
      isShowingNextQuestion = false,
      isShowingActionButtons = true,
      isShowingRangeButtonVisible = false,
    )

    // Changes state
    _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
  }


  private suspend fun validatesQuestionAnswer(shouldHaveAction: Boolean) = withContext(dispatchers.default) {
    // Gets the current state
    val question = currentRenderedState?.question ?: return@withContext

    // Finds the right range of hands that should be validated according to the action and stack.
    val targetRange = currentRanges.first { range ->
      range.action == question.action && range.effectiveStack == question.stack
    }

    // Gets the hands of that range for the target positon.
    val handsForTheAction = targetRange.handPosition.first { rangePosition ->
      rangePosition.heroPosition == question.heroPosition
    }.hands.map { it.hand }

    // Checks if the question hand is on the range.
    val questionRequiresAction = handsForTheAction.contains(question.asRangeHand())

    // Checks if answer is correct
    val isAnswerCorrect = when {
      shouldHaveAction && questionRequiresAction -> true
      shouldHaveAction && !questionRequiresAction -> false
      !shouldHaveAction && questionRequiresAction -> false
      else -> true
    }

    // Gets the taken action
    val takenAction = if (shouldHaveAction) question.action else RangeAction.FOLD

    // Creates persistence entity
    saveRangePracticeAnswerUseCase(
      question = question,
      takenAction = takenAction,
      isAnswerCorrect = isAnswerCorrect,
    )

    // Changes the state
    currentRenderedState = currentRenderedState?.copy(
      infoMessage = if (isAnswerCorrect) R.string.that_is_correct else R.string.that_is_not_correct,
      isShowingNextQuestion = true,
      isShowingActionButtons = false,
      isShowingRangeButtonVisible = false,
    )

    // Emits state
    withContext(dispatchers.main) {
      _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
    }
  }

  private fun resolveQuestionStack(): Int {
    return currentLearningFilter.stack ?: currentRanges.map { it.effectiveStack }.random()
  }

  private fun resolveQuestionCards(): String {
    val firstCard = Deck.cards.random()
    var secondCard: Card
    do {
      secondCard = Deck.cards.random()
    } while (secondCard == firstCard)

    // Make sure to bring the highest card first
    return if (firstCard.value > secondCard.value) {
      firstCard.formattedName() + secondCard.formattedName()
    } else {
      secondCard.formattedName() + firstCard.formattedName()
    }
  }

  private fun resolveHeroPosition(): RangePlayerPosition {
    return currentLearningFilter.heroPosition ?: currentRanges
      .map { it.handPosition }
      .flatten()
      .map { it.heroPosition }
      .distinct()
      .random()
  }

  private fun resolveVillainPosition(): RangePlayerPosition? {
    // Hardcoded open action for now given the app limitation of only warmup open actions
    return null
  }

  private fun resolveAction(): RangeAction {
    // Hardcoded open action for now given the app limitation
    return RangeAction.OPEN
//    return currentLearningFilter.action ?: currentRanges
//      .map { it.action }
//      .distinct()
//      .random()
  }

  private fun countOfAppliedFilter(): Int {
    var count = 0
    if(currentLearningFilter.stack != null) {
      count++
    }
    if(currentLearningFilter.heroPosition != null) {
      count++
    }
    if(currentLearningFilter.action != null) {
      count++
    }
    return count
  }
}