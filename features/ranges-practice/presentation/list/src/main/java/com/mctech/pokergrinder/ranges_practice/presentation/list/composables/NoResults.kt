package com.mctech.pokergrinder.ranges_practice.presentation.list.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mctech.pokergrinder.design.components.EmptyContentUi
import com.mctech.pokergrinder.ranges_practice.presentation.list.RangePracticeListInteraction

@Composable
internal fun NoResultsUi(
  message: String,
  interact: (RangePracticeListInteraction) -> Unit = {},
) {
  EmptyContentUi(
    message = message,
    buttonsContent = {
      BottomButtons(interact = interact, modifier = Modifier.align(Alignment.BottomCenter))
    }
  )
}