package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mctech.pokergrinder.design.compose.colorProvider
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun SeeRangeButton(
  modifier: Modifier = Modifier,
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  TextButton(
    onClick = { interact(RangePracticeInteraction.SeeRangeButtonClicked) },
    modifier = modifier,
  ) {
    Text(
      text = stringResource(id = R.string.see_range).uppercase(),
      style = typoProvider().button.copy(
        color = colorProvider().primary,
      ),
    )
  }
}