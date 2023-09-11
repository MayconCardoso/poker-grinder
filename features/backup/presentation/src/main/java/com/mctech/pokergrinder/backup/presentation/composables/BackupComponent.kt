package com.mctech.pokergrinder.backup.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.backup.presentation.BackupComponentState
import com.mctech.pokergrinder.backup.presentation.BackupInteraction
import com.mctech.pokergrinder.backup.presentation.BackupViewModel
import com.mctech.pokergrinder.design.components.CircularLoadingIndicator
import com.mctech.pokergrinder.localization.R

@Composable
fun BackupComponent() {
  // Gets view model
  val viewModel = hiltViewModel<BackupViewModel>().apply {
    initialize()
  }

  // Gets state
  val state = viewModel.state.collectAsState().value

  // Creates event consumer.
  val eventConsumer: (BackupInteraction) -> Unit = { userInteraction ->
    viewModel.interact(userInteraction)
  }

  // Resolves the balance text according to the state.
  when (state) {
    is ComponentState.Loading -> CircularLoadingIndicator()
    is ComponentState.Error -> NoResultsUi(
      message = stringResource(id = R.string.something_went_wrong),
      interact = eventConsumer,
    )

    is ComponentState.Success -> BackupUi(
      state = state.result,
      interact = eventConsumer,
    )
  }
}

@Composable
internal fun BackupUi(
  state: BackupComponentState,
  interact: (BackupInteraction) -> Unit = {},
) {
  // Draws empty list text if there is no transactions
  if (state.availableBackups.isEmpty()) {
    NoResultsUi(
      message = stringResource(id = R.string.no_backup),
      interact = interact,
    )
    return
  }

  // Draws transactions items.
  BackupList(items = state.availableBackups, interact = interact)

  // Confirmation dialog
  if (state.isShowingConfirmationDialog) {
    RestoreConfirmationDialog(interact = interact)
  }
}
