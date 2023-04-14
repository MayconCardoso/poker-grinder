package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.colorProvider
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.presentation.practice.RangePracticeInteraction

@Composable
internal fun HeaderUi(
  modifier: Modifier = Modifier,
  countOfAppliedFilter: Int = 0,
  interact: (RangePracticeInteraction) -> Unit = {},
) {
  // Draws the outer card.
  Box(
    modifier = modifier
      .background(PokerGrinder.LocalColors.current.primary)
      .fillMaxWidth()
      .padding(start = 15.dp, end = 12.dp)
      .height(60.dp)
  ) {
    Text(
      modifier = Modifier.align(Alignment.CenterStart),
      text = stringResource(id = R.string.warm_up),
      style = TextStyle(
        color = PokerGrinder.LocalColors.current.textColorInverted,
        fontSize = 19.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
      ),
    )

    FilterIcon(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .clickable {
          interact(RangePracticeInteraction.OnFilterClicked)
        },
      countOfAppliedFilter = countOfAppliedFilter,
    )
  }
}

@Composable
internal fun FilterIcon(
  modifier: Modifier = Modifier,
  countOfAppliedFilter: Int = 0
) {
  Box(
    modifier = modifier.height(34.dp).width(34.dp),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      imageVector = Icons.Filled.Settings,
      tint = MaterialTheme.colors.onPrimary,
      contentDescription = null,
      modifier = Modifier
        .size(28.dp)
        .clip(MaterialTheme.shapes.medium),
    )
    if (countOfAppliedFilter > 0) {
      Text(
        text = "3",
        textAlign = TextAlign.Center,
        style = TextStyle(
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = colorProvider().textColorInverted
        ),
        modifier = Modifier
          .width(16.dp)
          .height(16.dp)
          .align(Alignment.TopEnd)
          .background(
            color = colorProvider().primary,
            shape = CircleShape
          )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun HeaderUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    HeaderUi()
  }
}