package com.mctech.pokergrinder.ranges_practice.presentation.filter.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.components.HeaderToolbarUi
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.filter.RangePracticeFilterInteraction
import com.mctech.pokergrinder.ranges_practice.presentation.filter.RangePracticeFilterState

@Composable
internal fun FilterUi(
  state: RangePracticeFilterState,
  interact: (RangePracticeFilterInteraction) -> Unit,
) {
  Column(modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight()) {
    // Renders filter
    HeaderToolbarUi(title = stringResource(id = R.string.choose_the_spot))

    // Renders effective stack
    HorizontalSection(
      title = stringResource(id = R.string.effective_stack),
      options = state.stackOption,
      clickedOption = { option ->
        interact(RangePracticeFilterInteraction.OnStackClicked(option))
      },
    )

    // Renders hero position
    HorizontalSection(
      title = stringResource(id = R.string.hero_position),
      options = state.positionOption,
      clickedOption = { option ->
        interact(RangePracticeFilterInteraction.OnHeroPositionClicked(option))
      },
    )

    // Empty space
    Spacer(modifier = Modifier.weight(1F))

    // Draw save button
    Button(
      onClick = {
        interact(RangePracticeFilterInteraction.OnSaveClicked)
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Text(
        text = stringResource(id = R.string.save).uppercase(),
        style = typoProvider().button,
      )
    }
  }
}

@Composable
@Preview(showBackground = true)
internal fun FilterUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    FilterUi(
      state = RangePracticeFilterState(
        stackOption = listOf(),
        positionOption = listOf(),
      ),
      interact = {},
    )
  }
}