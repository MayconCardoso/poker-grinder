package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.domain.entities.Settings
import com.mctech.pokergrinder.settings.testing.newSettings
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveAllSettingsUseCaseTest {

  private val repository = mockk<SettingsRepository>(relaxed = true)
  private val target = ObserveAllSettingsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<Settings>>> {
    val items = listOf(
      newSettings(key = "1"),
      newSettings(key = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observeSettings() } returns flow
    }

    whenAction {
      target.invoke()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(items)
    }
  }
}