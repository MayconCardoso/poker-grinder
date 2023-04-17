package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges.domain.entities.RangeAction
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun BottomButtonsActions(
  action: RangeAction,
  modifier: Modifier = Modifier,
  interact: (RangePracticeInteraction) -> Unit = {}
) {
  // Creates the row for drawing both buttons.
  Column(
    modifier = modifier.fillMaxWidth()
  ) {
    // Creates action button
    BottomButton(text = action.name) {
      interact(RangePracticeInteraction.OnActionClicked)
    }

    // Creates space between buttons
    Spacer(modifier = Modifier.width(12.dp))

    // Creates fold button
    BottomButton(text = stringResource(id = R.string.fold)) {
      interact(RangePracticeInteraction.OnFoldClicked)
    }
  }
}

@Composable
internal fun BottomNextQuestionButton(
  modifier: Modifier = Modifier,
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  Button(
    modifier = modifier.fillMaxWidth(),
    onClick = {
      interact(RangePracticeInteraction.OnNextQuestionClicked)
    },
  ) {
    Text(
      text = stringResource(id = R.string.next_question).uppercase(),
      style = typoProvider().button,
    )
  }
}

@Composable
internal fun BottomButton(
  text: String,
  onClick: () -> Unit,
) {
  Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
    Text(text = text.uppercase(), style = typoProvider().button)
  }
}

@Preview(showBackground = true)
@Composable
internal fun BottomButtonsPreview() {
  PokerGrinder.PokerGrinderTheme {
    BottomButtonsActions(action = RangeAction.OPEN)
  }
}

@Preview(showBackground = true)
@Composable
internal fun BottomNextQuestionButtonPreview() {
  PokerGrinder.PokerGrinderTheme {
    BottomNextQuestionButton()
  }
}