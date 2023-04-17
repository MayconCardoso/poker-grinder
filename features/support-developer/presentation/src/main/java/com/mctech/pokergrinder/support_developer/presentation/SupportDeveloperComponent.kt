package com.mctech.pokergrinder.support_developer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.design.components.HeaderToolbarUi
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.colorProvider
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R

@Composable
internal fun SupportDeveloperComponent() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    HeaderToolbarUi(
      title = stringResource(id = R.string.help_developer_toolbar),
    )
    Text(
      modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
      text = stringResource(id = R.string.help_developer_title),
      textAlign = TextAlign.Center,
      style = typoProvider().caption.copy(
        fontSize = 18.sp,
      ),
    )
    Text(
      modifier = Modifier.padding(12.dp),
      textAlign = TextAlign.Center,
      text = stringResource(id = R.string.help_developer_message),
      style = typoProvider().body1.copy(
        fontSize = 16.sp,
      ),
    )
    Text(
      modifier = Modifier.padding(top = 24.dp),
      text = stringResource(id = R.string.poker_stars),
      textAlign = TextAlign.Center,
      style = typoProvider().caption.copy(
        fontSize = 18.sp,
      ),
    )
    Text(
      text = stringResource(id = R.string.my_nickname),
      textAlign = TextAlign.Center,
      style = typoProvider().caption.copy(
        fontSize = 18.sp,
        color = colorProvider().primary
      ),
    )
  }
}

@Composable
@Preview(showBackground = true)
internal fun SupportDeveloperComponentPreview() {
  PokerGrinder.PokerGrinderTheme {
    SupportDeveloperComponent()
  }
}