package com.mctech.pokergrinder.backup.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.backup.domain.BackupRepository
import com.mctech.pokergrinder.backup.domain.entities.Backup
import com.mctech.pokergrinder.backup.domain.entities.BackupState
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class RestoreDataUseCaseTest {
  private val repository = mockk<BackupRepository>(relaxed = true)
  private val useCase = RestoreDataUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate call to repository`() = responseScenario<Flow<BackupState>> {
    val mockkFlow = flow<BackupState> {}
    val backup = mockk<Backup>()

    givenScenario {
      coEvery { repository.restoreData(backup) } returns mockkFlow
    }

    whenAction {
      useCase(backup)
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(mockkFlow)
      coVerifyOrder {
        repository.restoreData(backup)
      }
    }
  }
}