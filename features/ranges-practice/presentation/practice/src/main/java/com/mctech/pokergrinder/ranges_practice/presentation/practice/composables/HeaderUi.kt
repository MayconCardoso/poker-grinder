package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun HeaderUi(
  modifier: Modifier = Modifier,
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  // Draws the outer card.
  Column(
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .background(PokerGrinder.LocalColors.current.primary)
      .fillMaxWidth()
      .padding(start = 15.dp, end = 15.dp)
      .height(60.dp)
  ) {
    Text(
      text = stringResource(id = R.string.warm_up),
      style = TextStyle(
        color = PokerGrinder.LocalColors.current.textColorInverted,
        fontSize = 19.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
      ),
    )
  }
}
@Preview(showBackground = true)
@Composable
internal fun HeaderUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderUi()
  }
}