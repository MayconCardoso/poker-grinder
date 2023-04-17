package com.mctech.pokergrinder.ranges_practice.presentation.filter

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.domain.entities.RangePlayerPosition
import com.mctech.pokergrinder.ranges.domain.usecases.ObserveAllRangesUseCase
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeFilter
import com.mctech.pokergrinder.ranges_practice.domain.usecases.ObserveRangePracticeFilterUseCase
import com.mctech.pokergrinder.ranges_practice.domain.usecases.SaveRangePracticeFilterUseCase
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RangePracticeFilterViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val observeAllRangesUseCase: ObserveAllRangesUseCase,
  private val saveRangePracticeFilterUseCase: SaveRangePracticeFilterUseCase,
  private val observeRangePracticeFilterUseCase: ObserveRangePracticeFilterUseCase,
) : BaseViewModel() {

  // region Variables

  private var currentRanges = listOf<Range>()
  private var currentRenderedState: RangePracticeFilterState? = null
  private var currentLearningFilter = RangePracticeFilter()

  private val _state by lazy { MutableStateFlow<ComponentState<RangePracticeFilterState>>(ComponentState.Loading.FromEmpty) }
  val state: StateFlow<ComponentState<RangePracticeFilterState>> by lazy { _state }

  // endregion

  // region State Manipulation

  override suspend fun initializeComponents() {
    observeRangePracticeFilter()
  }

  /**
   * Observes changes on both filter and ranges.
   */
  private fun observeRangePracticeFilter() {
    observeRangePracticeFilterUseCase()
      .combine(observeAllRangesUseCase()) { filter, ranges ->
        // Hold data for future usage.
        currentRanges = ranges
        currentLearningFilter = filter

        // Generates next state.
        generatesFilterState()
      }
      .launchIn(viewModelScope)
  }

  /**
   * Generates a new filter state.
   */
  private suspend fun generatesFilterState() = viewModelScope.launch(dispatchers.default) {
    // Gets defined stack on filter or a random one.
    val stackTask = async { buildStackOptions() }
    // Gets defined stack on filter or a random one.
    val heroPositionTask = async { buildHeroPositionOptions() }
    // Await all tasks
    awaitAll(stackTask, heroPositionTask)
    // Creates state
    currentRenderedState = RangePracticeFilterState(
      stackOption = stackTask.await(),
      positionOption = heroPositionTask.await(),
    )
    // Emit the generated state.
    withContext(dispatchers.main) {
      _state.value = ComponentState.Success(requireNotNull(currentRenderedState))
    }
  }

  /**
   * Creates a list of options for the effective stack.
   */
  private fun buildStackOptions(): List<RangeFilterOption> = buildList {
    // Add all
    this += RangeFilterOption(name = null, isSelected = currentLearningFilter.stack == null)

    // Add for each range stack
    this += currentRanges.map {
      RangeFilterOption(
        name = it.effectiveStack.toString(),
        isSelected = currentLearningFilter.stack == it.effectiveStack,
      )
    }
  }

  /**
   * Creates a list of options for the hero position.
   */
  private fun buildHeroPositionOptions(): List<RangeFilterOption> = buildList {
    // Add all
    this += RangeFilterOption(name = null, isSelected = currentLearningFilter.heroPosition == null)
    // Add for each range stack
    this += currentRanges
      .asSequence()
      .map { it.handPosition }
      .flatten()
      .map { it.heroPosition.name }
      .distinct()
      .map { name ->
        RangeFilterOption(
          name = name,
          isSelected = currentLearningFilter.heroPosition?.name == name,
        )
      }
      .toList()
  }

  // endregion

  // region Interactions

  @OnInteraction(RangePracticeFilterInteraction.OnStackClicked::class)
  private suspend fun onStackClickedInteraction(
    interaction: RangePracticeFilterInteraction.OnStackClicked
  ) {
    // Creates a new filter
    val filter = currentLearningFilter.copy(
      stack = interaction.option.name?.toInt()
    )

    // Save filter
    saveRangePracticeFilterUseCase(filter)
  }

  @OnInteraction(RangePracticeFilterInteraction.OnHeroPositionClicked::class)
  private suspend fun onHeroPositionClickedInteraction(
    interaction: RangePracticeFilterInteraction.OnHeroPositionClicked
  ) {
    // Creates a new filter
    val filter = currentLearningFilter.copy(
      heroPosition = interaction.option.name?.let { RangePlayerPosition.valueOf(it) }
    )

    // Save filter
    saveRangePracticeFilterUseCase(filter)
  }

  @OnInteraction(RangePracticeFilterInteraction.OnSaveClicked::class)
  private suspend fun onSaveClickedInteraction() {
    // Close filter screen
    sendCommand(RangePracticeFilterCommands.NavigateBack)
  }

  // endregion
}