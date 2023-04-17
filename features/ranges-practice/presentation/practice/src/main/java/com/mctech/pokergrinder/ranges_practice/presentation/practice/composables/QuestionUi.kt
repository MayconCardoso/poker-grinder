package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeState

@Composable
internal fun QuestionUi(
  state: RangePracticeState,
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  // Draws the outer card.
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
  ) {
    // Draw top section
    Column(
      modifier = Modifier.align(Alignment.TopCenter),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // Draw header
      HeaderUi(
        interact = interact,
        countOfAppliedFilter = state.countAppliedFilter,
      )

      // Top Spacer
      Spacer(modifier = Modifier.height(24.dp))

      // Draw hand context
      HandContextUi(state = state)

      // Draw cards.
      CardViewer(cards = state.question.cards)

      // See range button
      SeeRangeButton(interact = interact)

      // Draw info message
      AnimatedVisibility(state.infoMessage != null) {
        Text(
          text = state.infoMessage?.let { stringResource(id = it) }.orEmpty(),
          textAlign = TextAlign.Center,
          style = typoProvider().h2,
        )
      }
    }

    // Draw action buttons
    if (state.isShowingActionButtons) {
      BottomButtonsActions(
        action = state.question.action,
        interact = interact,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(12.dp)
      )
    }

    // Draw next question buttons
    if (state.isShowingNextQuestion) {
      BottomNextQuestionButton(
        interact = interact,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(12.dp)
      )
    }
  }
}