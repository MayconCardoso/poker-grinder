package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.Backup
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class GetAvailableBackupUseCaseTest {
  private val repository = mockk<BackupRepository>(relaxed = true)
  private val useCase = GetAvailableBackupUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate call to repository`() = responseScenario<List<Backup>> {
    val data = listOf<Backup>()

    givenScenario {
      coEvery { repository.loadAvailableBackup() } returns data
    }

    whenAction {
      useCase()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(data)
      coVerifyOrder {
        repository.loadAvailableBackup()
      }
    }
  }
}