package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.colorProvider
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeState

@Composable
internal fun HandContextUi(state: RangePracticeState) {
  // Draw Position
  Text(
    text = stringResource(id = R.string.position),
    textAlign = TextAlign.Center,
    style = typoProvider().h2,
  )
  Text(
    text = state.question.position.name,
    textAlign = TextAlign.Center,
    style = typoProvider().h2.copy(
      fontWeight = FontWeight.Bold,
      color = colorProvider().textColorSecondary
    ),
  )
  Spacer(modifier = Modifier.height(12.dp))

  // Draw Effective Stack
  Text(
    text = stringResource(id = R.string.effective_stack),
    textAlign = TextAlign.Center,
    style = typoProvider().h2,
  )
  Text(
    text = "${state.question.stack}BB",
    textAlign = TextAlign.Center,
    style = typoProvider().h2.copy(
      fontWeight = FontWeight.Bold,
      color = colorProvider().textColorSecondary
    ),
  )
}