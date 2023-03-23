package com.mctech.pokergrinder.bankroll.presentation.list.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransaction
import com.mctech.pokergrinder.bankroll.domain.entities.BankrollTransactionType
import com.mctech.pokergrinder.bankroll.presentation.list.BankrollComponentEventConsumer
import com.mctech.pokergrinder.design.components.MoneyIndicator
import com.mctech.pokergrinder.design.compose.PokerGrinder
import com.mctech.pokergrinder.design.compose.typoProvider
import com.mctech.pokergrinder.design.extension.fillScreen

@Composable
internal fun TransactionList(
  items: List<BankrollTransaction>,
  consumer: BankrollComponentEventConsumer,
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
      items(items) { transaction ->
        TransactionItemRow(transaction = transaction)
      }
    }

    // Draw bottom buttons
    BottomButtons(consumer = consumer)
  }
}

@Composable
internal fun TransactionItemRow(transaction: BankrollTransaction) {
  // Creates card modifier with accessibility info.
  val cardModifier = Modifier
    .fillMaxWidth()
    .height(IntrinsicSize.Min)
    .semantics {
      contentDescription = "Item${transaction.description}-${transaction.amount}"
    }

  // Creates the card.
  Card(modifier = cardModifier) {
    Row {
      // Draws money indicator based on balance.
      MoneyIndicator(amount = transaction.amount)

      // Draws transaction info.
      Column(
        modifier = Modifier
          .padding(8.dp)
          .weight(1F)
      ) {
        Text(text = transaction.description, style = typoProvider().caption)
        Text(text = transaction.formattedDate(), style = typoProvider().body1)
      }

      // Draws transaction amount.
      Text(
        text = transaction.formattedAmount(),
        style = typoProvider().body1.copy(
          fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier
          .align(Alignment.CenterVertically)
          .padding(end = 8.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemPreview() {
  PokerGrinder.PokerGrinderTheme {
    TransactionList(
      listOf(
        BankrollTransaction(
          id = "1",
          type = BankrollTransactionType.DEPOSIT,
          description = "This is a mock transaction",
          amount = 100.0,
          dateInMs = 100000000000,
        ),
        BankrollTransaction(
          id = "1",
          type = BankrollTransactionType.DEPOSIT,
          description = "This is a mock transaction",
          amount = -100.0,
          dateInMs = 100000000000,
        ),
      ),
      consumer = {}
    )
  }
}

@Preview(showBackground = true)
@Composable
internal fun TransactionItemRowPreview() {
  PokerGrinder.PokerGrinderTheme {
    TransactionItemRow(
      BankrollTransaction(
        id = "1",
        type = BankrollTransactionType.DEPOSIT,
        description = "This is a mock transaction",
        amount = 100.0,
        dateInMs = 100000000000,
      )
    )
  }
}
