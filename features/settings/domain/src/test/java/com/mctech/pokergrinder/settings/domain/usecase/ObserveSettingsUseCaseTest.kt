package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.domain.entities.SettingsAvailable
import com.mctech.pokergrinder.settings.testing.newSettings
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveSettingsUseCaseTest {

  private val repository = mockk<SettingsRepository>(relaxed = true)
  private val target = ObserveSettingsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<Settings?>> {
    val flow = flow { emit(newSettings()) }

    givenScenario {
      every { repository.observeSetting(SettingsAvailable.GROUP_TOURNAMENTS.key) } returns flow
    }

    whenAction {
      target.invoke(SettingsAvailable.GROUP_TOURNAMENTS)
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(newSettings())
    }
  }
}