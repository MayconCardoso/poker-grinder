package com.mctech.pokergrinder.tournament.presentation.list_component.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.domain.entities.TournamentType
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListCallback

@Composable
internal fun TransactionList(
  modifier: Modifier = Modifier,
  items: List<Tournament>,
  callback: TournamentListCallback,
) {
  // Draw List with Transactions
  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 12.dp)
  ) {
    items(items) { tournament ->
      TournamentItemRow(tournament = tournament, callback = callback)
    }
  }
}

@Composable
internal fun TournamentItemRow(
  tournament: Tournament,
  callback: TournamentListCallback,
) {
  // Creates card modifier with accessibility info.
  val cardModifier = Modifier
    .fillMaxWidth()
    .height(IntrinsicSize.Min)
    .semantics {
      contentDescription = "Item${tournament.title}-${tournament.buyIn}"
    }

  // Creates the card.
  Card(
    modifier = cardModifier.clickable {
      callback.onTournamentClicked(tournament)
    }
  ) {
    // Draws tournament info.
    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        text = tournament.title,
        style = typoProvider().caption,
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "${stringResource(id = R.string.buy_in)}\n${tournament.formattedBuyIn()}",
        style = typoProvider().body1,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemPreview() {
  PokerGrinder.PokerGrinderTheme {
    TransactionList(
      items = listOf(
        Tournament(
          id = "1",
          type = TournamentType.TURBO,
          buyIn = 100.0F,
          title = "This is a mock transaction",
          countReBuy = 1,
          guaranteed = 100,
          isBounty = true,
          startTimeInMs = 1,
        ),
        Tournament(
          id = "1",
          type = TournamentType.TURBO,
          buyIn = 100.0F,
          title = "This is a mock transaction",
          countReBuy = 1,
          guaranteed = 100,
          isBounty = true,
          startTimeInMs = 1,
        ),
      ),
      callback = { },
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemRowPreview() {
  PokerGrinder.PokerGrinderTheme {
    TournamentItemRow(
      tournament = Tournament(
        id = "1",
        type = TournamentType.TURBO,
        buyIn = 100.0F,
        title = "This is a mock transaction",
        countReBuy = 1,
        guaranteed = 100,
        isBounty = true,
        startTimeInMs = 1,
      ),
      callback = {

      }
    )
  }
}
