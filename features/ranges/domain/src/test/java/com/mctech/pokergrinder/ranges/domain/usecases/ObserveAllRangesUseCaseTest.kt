package com.mctech.pokergrinder.ranges.domain.usecases

import com.mctech.common_test.TestScenario.Companion.responseScenario
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

class ObserveAllRangesUseCaseTest {

  private val repository = mockk<RangesRepository>(relaxed = true)
  private val target = ObserveAllRangesUseCase(
    repository = repository,
  )

  @Test
  fun `should delegate repository flow`() = responseScenario<Flow<List<Range>>> {
    val items = listOf(
      newRange(id = "1"),
      newRange(id = "2"),
    )

    val flow = flow { emit(items) }

    givenScenario {
      every { repository.observeAllRanges() } returns flow
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