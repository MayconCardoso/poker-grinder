package com.mctech.pokergrinder.settings.domain.usecase

import com.mctech.common_test.TestScenario.Companion.simpleScenario
import com.mctech.pokergrinder.settings.domain.SettingsRepository
import com.mctech.pokergrinder.settings.testing.newSettings
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import org.junit.Test

class SaveSettingsUseCaseTest {

  private val repository = mockk<SettingsRepository>(relaxed = true)
  private val target = SaveSettingsUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = simpleScenario {
    val items = listOf(
      newSettings(key = "1"),
      newSettings(key = "2"),
    )

    whenAction {
      items.forEach {
        target.invoke(it)
      }
    }

    thenAssert {
      items.forEach { settings ->
        coVerify { repository.save(settings) }
      }
      confirmVerified(repository)
    }
  }
}