package com.mctech.pokergrinder.ranges.domain.usecases

import com.mctech.common_test.TestScenario
import com.mctech.pokergrinder.ranges.domain.RangesRepository
import com.mctech.pokergrinder.ranges.domain.entities.Range
import com.mctech.pokergrinder.ranges.testing.newRange
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ObserveRangeUseCaseTest {

  private val repository = mockk<RangesRepository>(relaxed = true)
  private val target = ObserveRangeUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = TestScenario.responseScenario<Flow<Range>> {
    val flow = flow { emit(newRange(id = "1")) }

    givenScenario {
      every { repository.observeRange(any()) } returns flow
    }

    whenAction {
      target.invoke(newRange(id = "1"))
    }

    thenAssert { result ->
      assertThat(result).isEqualTo(flow)
      assertThat(result.last()).isEqualTo(newRange(id = "1"))
    }
  }
}