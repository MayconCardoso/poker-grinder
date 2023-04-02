package com.mctech.pokergrinder.tournament.presentation.creation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.components.TextField
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen
import com.mctech.pokergrinder.localization.R
import com.mctech.pokergrinder.tournament.domain.entities.Tournament
import com.mctech.pokergrinder.tournament.presentation.creation.NewTournamentInteraction

@Composable
internal fun NewTournamentUi(
  tournamentState: Tournament? = null,
  interact: (NewTournamentInteraction) -> Unit = {},
) {
  // Hold fields value.
  val title = remember { mutableStateOf(TextFieldValue(text = tournamentState?.title.orEmpty())) }
  val buyIn = remember { mutableStateOf(TextFieldValue(text = tournamentState?.buyIn?.toString().orEmpty())) }
  val isSaveEnabled = remember { mutableStateOf(title.value.text.isNotBlank()) }
  val computeButtonEnabled = {
    isSaveEnabled.value = title.value.text.isNotBlank() && buyIn.value.text.isNotBlank()
  }

  // Draws the component.
  Column(modifier = Modifier.fillScreen()) {
    // Render title
    Text(
      text = stringResource(id = R.string.tournament_management),
      style = typoProvider().h2
    )

    // Draw a space
    Spacer(modifier = Modifier.height(6.dp))

    // Draw title field.
    TextField(
      text = stringResource(id = R.string.title),
      value = title,
      onValueChange = {
        computeButtonEnabled()
      },
    )

    // Draw amount field.
    TextField(
      text = stringResource(id = R.string.buy_in),
      value = buyIn,
      onValueChange = {
        computeButtonEnabled()
      },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )

    // Draw the button
    Button(
      enabled = isSaveEnabled.value,
      onClick = {
        interact(
          NewTournamentInteraction.SaveTournament(
            title = title.value.text,
            buyIn = buyIn.value.text.toFloat(),
          )
        )
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = stringResource(id = R.string.save).uppercase(),
        style = typoProvider().button
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun NewTournamentUiPreview() {
  PokerGrinder.PokerGrinderTheme {
    NewTournamentUi()
  }
}