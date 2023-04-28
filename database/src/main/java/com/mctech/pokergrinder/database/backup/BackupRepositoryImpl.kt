package com.mctech.pokergrinder.database.backup

import android.app.Application
import com.google.gson.GsonBuilder
import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.Backup
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import com.mctech.pokergrinder.database.PokerGrinderDatabase
import com.mctech.pokergrinder.formatter.asFormattedFullDate
import com.mctech.pokergrinder.threading.CoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import javax.inject.Inject

class BackupRepositoryImpl @Inject constructor(
  private val application: Application,
  private val database: PokerGrinderDatabase,
  private val dispatchers: CoroutineDispatchers,
) : BackupRepository {

  private val fileExtension = ".pokergrinder"

  private var stateFlow: MutableStateFlow<BackupState>? = null

  private val backupFolder by lazy {
    File("${application.filesDir}/backup/").apply {
      mkdirs()
    }
  }

  private val gson by lazy {
    GsonBuilder()
      .setPrettyPrinting()
      .create()
  }

  override fun prepareFlow(): Flow<BackupState> {
    return MutableStateFlow<BackupState>(BackupState.InProgress(0.0)).apply {
      stateFlow = this
    }
  }

  override suspend fun restoreData(backup: Backup) {
    TODO("Not yet implemented")
  }

  override suspend fun backupData() = withContext(dispatchers.io) {
    var backupData: BackupData? = null

    // Starts a transaction
    database.runInTransaction {
      // Gets tournaments
      stateFlow?.value = BackupState.InProgress(10.0)
      val tournaments = database.tournamentDao().loadAll()

      // Gets bankroll
      stateFlow?.value = BackupState.InProgress(20.0)
      val bankroll = database.bankrollTransactionDao().loadAll()

      // Gets sessions
      stateFlow?.value = BackupState.InProgress(50.0)
      val sessions = database.grindDao().loadAll()

      // Gets sessions tournaments
      stateFlow?.value = BackupState.InProgress(60.0)
      val sessionsTournaments = database.grindTournamentDao().loadAll()

      // Gets sessions tournaments flips
      stateFlow?.value = BackupState.InProgress(70.0)
      val sessionsTournamentsFlips = database.grindGameplayDao().loadAll()

      // Gets range practice
      stateFlow?.value = BackupState.InProgress(80.0)
      val rangePractice = database.rangePracticeDao().loadAll()

      // Create entity to save
      backupData = BackupData(
        tournaments = tournaments,
        bankrollTransactions = bankroll,
        sessions = sessions,
        sessionsTournament = sessionsTournaments,
        sessionsTournamentFlips = sessionsTournamentsFlips,
        rangePractice = rangePractice,
      )
    }

    // Create the file
    val newFile = File(backupFolder, "${Calendar.getInstance().timeInMillis}$fileExtension").apply {
      delete()
      createNewFile()
    }

    // Save file
    FileOutputStream(newFile.path).use {
      it.write(gson.toJson(backupData).toByteArray())
    }

    // Finishes
    stateFlow?.value = BackupState.Finished
  }

  override suspend fun loadAvailableBackup(): List<Backup> = withContext(dispatchers.io) {
    backupFolder.listFiles().orEmpty().map {
      Backup(
        title = it.name.replace(fileExtension, "").toLong().asFormattedFullDate(),
        filePath = it.absolutePath,
        fileSize = it.length()
      )
    }
  }
}