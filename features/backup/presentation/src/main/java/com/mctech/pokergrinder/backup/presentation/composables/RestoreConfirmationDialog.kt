package com.mctech.pokergrinder.backup.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mctech.pokergrinder.backup.presentation.BackupInteraction
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.localization.R

@Composable
internal fun RestoreConfirmationDialog(interact: (BackupInteraction) -> Unit = {}) {
  // Shows dialog.
  AlertDialog(
    onDismissRequest = {
      interact(BackupInteraction.OnRestoreConfirmationRemoved)
    },
    title = {
      Text(
        modifier = Modifier.padding(top = 12.dp),
        text = stringResource(id = R.string.confirmation),
        style = typoProvider().h2,
      )
    },
    text = {
      Text(
        text = stringResource(id = R.string.backup_confirm_message),
        style = typoProvider().body2.copy(
          fontSize = 16.sp
        ),
        textAlign = TextAlign.Justify,
      )
    },
    confirmButton = {
      Button(
        onClick = {
          interact(BackupInteraction.OnRestoreBackupConfirmed)
        }
      ) {
        Text(
          text = stringResource(id = R.string.restore).uppercase(),
          style = typoProvider().button,
        )
      }
    },
    dismissButton = {
      Button(
        onClick = {
          interact(BackupInteraction.OnRestoreConfirmationRemoved)
        }
      ) {
        Text(
          text = stringResource(id = R.string.cancel).uppercase(),
          style = typoProvider().button,
        )
      }
    }
  )
}