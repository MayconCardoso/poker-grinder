package com.mctech.pokergrinder.ranges_practice.presentation.practice.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
internal fun CardViewer(modifier: Modifier = Modifier, cards: String) {
  val context = LocalContext.current
  val firstCard = cards.substring(0, 2).loadCardImageId(context)
  val secondCard = cards.substring(2, 4).loadCardImageId(context)

  Row(modifier = modifier) {
    Image(
      painter = painterResource(firstCard),
      contentDescription = "",
      modifier = Modifier.height(200.dp)
    )

    Spacer(modifier = Modifier.width(12.dp))

    Image(
      painter = painterResource(secondCard),
      contentDescription = "",
      modifier = Modifier.height(200.dp)
    )
  }
}

private fun String.loadCardImageId(context: Context): Int {
  return context.resources.getIdentifier("card_${this}", "drawable", context.packageName)
}