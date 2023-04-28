package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class BackupDataUseCaseTest {
  private val repository = mockk<BackupRepository>(relaxed = true)
  private val useCase = BackupDataUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate call to repository`() = responseScenario<Flow<BackupState>> {
    val mockkFlow = flow<BackupState> {}

    givenScenario {
      coEvery { repository.backupData() } returns mockkFlow
    }

    whenAction {
      useCase()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(mockkFlow)
      coVerifyOrder {
        repository.backupData()
      }
    }
  }
}