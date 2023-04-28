package com.mctech.pokergrinder.backup.presentation

import androidx.lifecycle.viewModelScope
import com.mctech.pokergrinder.architecture.BaseViewModel
import com.mctech.pokergrinder.architecture.ComponentState
import com.mctech.pokergrinder.architecture.OnInteraction
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import com.mctech.pokergrinder.backup.domain.usecases.BackupDataUseCase
import com.mctech.pokergrinder.backup.domain.usecases.GetAvailableBackupUseCase
import com.mctech.pokergrinder.backup.domain.usecases.RestoreDataUseCase
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class BackupViewModel @Inject constructor(
  private val dispatchers: CoroutineDispatchers,
  private val backupDataUseCase: BackupDataUseCase,
  private val restoreDataUseCase: RestoreDataUseCase,
  private val loadBackupUseCase: GetAvailableBackupUseCase,
) : BaseViewModel() {

  private val _state by lazy {
    MutableStateFlow<ComponentState<BackupComponentState>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<BackupComponentState>> by lazy { _state }

  override suspend fun initializeComponents() {
    loadBackups()
  }

  private suspend fun loadBackups() {
    val items = loadBackupUseCase()
    _state.value = ComponentState.Success(
      BackupComponentState(
        message = "",
        isShowingLoading = false,
        availableBackups = items
      )
    )
  }

  @OnInteraction(BackupInteraction.OnBackupButtonClicked::class)
  private suspend fun onBackupButtonClicked() {
    // Put component in loading
    _state.value = ComponentState.Loading.FromEmpty

    // Prepare backup
    backupDataUseCase.prepare()
      .flowOn(dispatchers.main)
      .onEach { state ->
        if (state is BackupState.Finished) {
          loadBackups()
        }
      }
      .launchIn(viewModelScope)

    // Start
    backupDataUseCase.doBackUp()
  }

  @OnInteraction(BackupInteraction.OnBackupClicked::class)
  private suspend fun onBackupButtonClicked(
    interaction: BackupInteraction.OnBackupClicked
  ) {
    // Put component in loading
    _state.value = ComponentState.Loading.FromEmpty

    // Prepare backup
    restoreDataUseCase.prepare()
      .flowOn(dispatchers.main)
      .onEach { state ->
        if (state is BackupState.Finished) {
          loadBackups()
        }
      }
      .launchIn(viewModelScope)

    // Start
    restoreDataUseCase.restore(interaction.backup)
  }

}