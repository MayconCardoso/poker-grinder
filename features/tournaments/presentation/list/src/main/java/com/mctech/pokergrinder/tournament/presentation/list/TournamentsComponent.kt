package com.mctech.pokergrinder.tournament.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListCallback
import com.mctech.pokergrinder.tournament.presentation.list_component.TournamentListComponent

@Composable
fun TournamentsComponent(
  callback: TournamentListCallback,
) {
  // Draws the outer card.
  Column(modifier = Modifier.fillScreen()) {
    TournamentListComponent(
      modifier = Modifier.weight(1F),
      callback = callback,
    )

    // Draw bottom buttons
    Button(
      onClick = {
        callback.onTournamentClicked(null)
      },
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(text = stringResource(id = R.string.new_tournament).uppercase(), style = typoProvider().button)
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun TournamentsComponentPreview() {
  PokerGrinder.PokerGrinderTheme {
    TournamentsComponent(callback = {})
  }
}