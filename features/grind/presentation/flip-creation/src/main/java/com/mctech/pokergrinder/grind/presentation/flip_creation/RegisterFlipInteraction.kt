package com.mctech.pokergrinder.grind.presentation.flip_creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.entities.Session
import com.mctech.pokergrinder.grind.presentation.flip_creation.adapter.RegisterFlipTournamentConsumerEvent

internal sealed class RegisterFlipInteraction : UserInteraction {

  data class ScreenFirstOpen(val session: Session) : RegisterFlipInteraction()

  data class OnTournamentEvent(val event: RegisterFlipTournamentConsumerEvent) : RegisterFlipInteraction()

  data class CardSelected(val card: Card) : RegisterFlipInteraction()

  data class CardUnselected(val card: Card) : RegisterFlipInteraction()

  object OnBackPressed : RegisterFlipInteraction()

  object HeroWonFlip : RegisterFlipInteraction()

  object VillainWonFlip : RegisterFlipInteraction()
}