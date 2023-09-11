package com.mctech.pokergrinder.backup.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mctech.pokergrinder.backup.presentation.BackupInteraction
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R

@Composable
internal fun BottomButtons(
  modifier: Modifier = Modifier,
  interact: (BackupInteraction) -> Unit = {}
) {
  Button(
    modifier = modifier.fillMaxWidth(),
    onClick = {
      interact(BackupInteraction.OnBackupButtonClicked)
    },
  ) {
    Text(
      text = stringResource(id = R.string.backup_data).uppercase(),
      style = typoProvider().button,
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun BottomButtonsPreview() {
  PokerGrinder.PokerGrinderTheme {
    BottomButtons()
  }
}