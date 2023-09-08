package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.architecture_testing.BaseViewModelTest
import com.mctech.architecture_testing.extensions.TestObserverScenario.Companion.observerScenario
import com.mctech.pokergrinder.grind.domain.GrindAnalytics
import com.mctech.pokergrinder.grind.domain.usecase.CreateNewSessionUseCase
import com.mctech.pokergrinder.grind.domain.usecase.NewSessionsSuggestedNameUseCase
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.filterNotNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class NewGrindViewModelTest : BaseViewModelTest() {
  private val analytics = mockk<GrindAnalytics>(relaxed = true)
  private val createNewSessionUseCase = mockk<CreateNewSessionUseCase>(relaxed = true)
  private val sessionNameSuggestionUseCase = mockk<NewSessionsSuggestedNameUseCase>(relaxed = true)
  private val target = NewGrindViewModel(
    analytics = analytics,
    createNewSessionUseCase = createNewSessionUseCase,
    sessionNameSuggestionUseCase = sessionNameSuggestionUseCase,
  )

  @Test
  fun `should initialize new session component`() = observerScenario {
    givenScenario {
      every { sessionNameSuggestionUseCase() } returns "Sep, 14th"
    }

    whenAction {
      target.initialize()
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).isEmpty()
    }

    thenAssertFlow(target.componentState.filterNotNull()) { states ->
      val expectedItem = NewGrindState(suggestedTitle = "Sep, 14th")
      assertThat(states).isEqualTo(listOf(expectedItem))
    }

    thenAssert {
      confirmVerified(createNewSessionUseCase, analytics)
    }
  }

  @Test
  fun `should create new session`() = observerScenario {
    whenAction {
      target.interact(NewGrindInteraction.SaveGrind(title = "My new session"))
    }

    thenAssertLiveData(target.commandObservable) { commands ->
      assertThat(commands).containsExactly(NewGrindCommand.CloseScreen)
    }

    thenAssert {
      coVerifyOrder {
        createNewSessionUseCase(title = "My new session")
        analytics.onSessionCreated(title = "My new session")
      }
      confirmVerified(createNewSessionUseCase, analytics)
    }
  }
}