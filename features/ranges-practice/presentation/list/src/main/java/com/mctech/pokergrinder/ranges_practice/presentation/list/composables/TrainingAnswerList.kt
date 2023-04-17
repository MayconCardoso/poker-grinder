package com.mctech.pokergrinder.ranges_practice.presentation.list.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.components.ColorIndicator
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.ranges_practice.domain.entities.RangePracticeResult
import com.mctech.pokergrinder.ranges_practice.presentation.list.RangePracticeListInteraction

@Composable
internal fun TrainingAnswerList(
  items: List<RangePracticeResult>,
  interact: (RangePracticeListInteraction) -> Unit = {},
) {
  // Draws the outer card.
  Column(modifier = Modifier.fillScreen()) {
    // Draw List with Transactions
    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp)
        .weight(1F)
    ) {
      items(items, key = { item -> item.id }) { transaction ->
        TrainingAnswerItemRow(item = transaction)
      }
    }

    // Draw bottom buttons
    BottomButtons(interact = interact)
  }
}

@Composable
internal fun TrainingAnswerItemRow(item: RangePracticeResult) {
  // Creates card modifier with accessibility info.
  val cardModifier = Modifier
    .fillMaxWidth()
    .height(70.dp)
    .semantics {
      contentDescription = "Item${item.cards}-${item.heroPosition.name}"
    }

  // Creates the card.
  Card(modifier = cardModifier) {
    Row {
      // Draws color indicator based on balance.
      ColorIndicator(success = item.isAnswerCorrect)

      // Draws action.
      ColumnInfo(title = stringResource(id = R.string.action), data = item.action)

      // Draws hero position.
      ColumnInfo(title = stringResource(id = R.string.hero), data = item.heroPosition.name)

      // Draws villain position.
      if(item.villainPosition != null) {
        ColumnInfo(title = stringResource(id = R.string.villain), data = item.villainPosition?.name.orEmpty())
      }

      // Draws stack.
      ColumnInfo(title = stringResource(id = R.string.stack), data = "${item.effectiveStack}BB")

      // Draws cards info.
      CardViewer(modifier = Modifier.align(CenterVertically), item = item)
    }
  }
}

@Composable
internal fun RowScope.ColumnInfo(title: String, data: String) {
  Column(
    modifier = Modifier
      .padding(6.dp)
      .weight(1F)
      .align(CenterVertically)
  ) {
    Text(
      modifier = Modifier.align(CenterHorizontally),
      text = title,
      style = typoProvider().body1.copy(
        fontWeight = FontWeight.Bold,
      ),
    )

    Text(
      modifier = Modifier.align(CenterHorizontally),
      text = data,
      style = typoProvider().subtitle1.copy(
        fontWeight = FontWeight.Bold,
      ),
    )
  }
}

@Composable
internal fun CardViewer(modifier: Modifier, item: RangePracticeResult) {
  val context = LocalContext.current
  val firstCard = item.cards.substring(0, 2).loadCardImageId(context)
  val secondCard = item.cards.substring(2, 4).loadCardImageId(context)

  Row(modifier = modifier.padding(8.dp)) {
    Image(
      painter = painterResource(firstCard),
      contentDescription = "",
      modifier = Modifier.height(60.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Image(
      painter = painterResource(secondCard),
      contentDescription = "",
      modifier = Modifier.height(60.dp)
    )
  }
}

private fun String.loadCardImageId(context: Context): Int {
  return context.resources.getIdentifier("card_${this}", "drawable", context.packageName)
}