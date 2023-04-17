package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.components.HeaderToolbarUi
import com.mctech.pokergrinder.design.components.ToolbarCounterIcon
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun HeaderUi(
  modifier: Modifier = Modifier,
  countOfAppliedFilter: Int = 0,
  interact: (RangePracticeInteraction) -> Unit,
) {
  // Draws the outer card.
  HeaderToolbarUi(
    title = stringResource(id = R.string.warm_up),
    modifier = modifier,
    iconContent = {
      ToolbarCounterIcon(
        icon = Icons.Filled.Settings,
        counterValue = countOfAppliedFilter,
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .clickable {
            interact(RangePracticeInteraction.OnFilterClicked)
          }
      )
    }
  )
}

@Preview(showBackground = true)
@Composable
internal fun HeaderUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderUi(
      countOfAppliedFilter = 3,
      interact = {},
    )
  }
}