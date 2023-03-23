package com.mctech.pokergrinder.design.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.colorProvider

@Composable
fun CircularLoadingIndicator(
  modifier: Modifier = Modifier
) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight(),
  ) {
    CircularProgressIndicator(
      color = colorProvider().primary,
      modifier = Modifier
        .width(30.dp)
        .height(30.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun CircularLoadingIndicatorPreview() {
  PokerGrinder.PokerGrinderTheme() {
    CircularLoadingIndicator()
  }
}