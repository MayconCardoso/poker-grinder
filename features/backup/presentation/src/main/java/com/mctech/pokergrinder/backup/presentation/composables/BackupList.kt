package com.mctech.pokergrinder.backup.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.backup.domain.entities.Backup
import com.mctech.pokergrinder.backup.presentation.BackupInteraction
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen

@Composable
internal fun BackupList(
  items: List<Backup>,
  interact: (BackupInteraction) -> Unit = {},
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
      items(items) { backup ->
        BackupItemRow(backup = backup, interact = interact)
      }
    }

    // Draw bottom buttons
    BottomButtons(interact = interact)
  }
}

@Composable
internal fun BackupItemRow(
  backup: Backup,
  interact: (BackupInteraction) -> Unit = {},
) {
  // Creates card modifier with accessibility info.
  val cardModifier = Modifier
    .fillMaxWidth()
    .height(IntrinsicSize.Min)
    .semantics {
      contentDescription = "Item${backup.title}"
    }

  // Creates the card.
  Card(
    modifier = cardModifier.clickable {
      interact(BackupInteraction.OnBackupClicked(backup))
    }
  ) {
    // Draws tournament info.
    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        text = backup.title,
        style = typoProvider().caption,
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "${backup.fileSize} KB",
        style = typoProvider().body1,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemPreview() {
  PokerGrinder.PokerGrinderTheme {
    BackupList(
      listOf(
        Backup(
          title = "10 de Nov de 2019",
          filePath = "",
          fileSize = 10,
        ),
        Backup(
          title = "11 de Nov de 2019",
          filePath = "",
          fileSize = 10,
        ),
      ),
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun BackupItemRowPreview() {
  PokerGrinder.PokerGrinderTheme {
    BackupItemRow(
      Backup(
        title = "10 de Nov de 2019",
        filePath = "",
        fileSize = 10,
      )
    )
  }
}
