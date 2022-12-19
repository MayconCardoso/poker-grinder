package com.mctech.pokergrinder.grind.domain.usecase

import com.mctech.common_test.CalendarTestRule
import com.mctech.common_test.TestScenario.Companion.responseScenario
import com.mctech.common_test.UuidTestRule
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class GenerateUniqueIdUseCaseTest {

  @get:Rule
  val uuidRule = UuidTestRule()

  @Test
  fun `should generate id`() = responseScenario<String> {
    givenScenario {
      every { UUID.randomUUID().toString() } returns "10"
    }

    whenAction {
      GenerateUniqueIdUseCase().invoke()
    }

    thenAssert { result ->
      assertThat(result).isEqualTo("10")
    }
  }

}