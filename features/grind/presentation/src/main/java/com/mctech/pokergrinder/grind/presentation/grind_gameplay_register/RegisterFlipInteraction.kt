package com.mctech.pokergrinder.grind.presentation.grind_gameplay_register

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.deck.domain.Card
import com.mctech.pokergrinder.grind.domain.entities.Session

internal sealed class RegisterFlipInteraction : UserInteraction {

  data class ScreenFirstOpen(val session: Session) : RegisterFlipInteraction()

  data class CardSelected(val card: Card) : RegisterFlipInteraction()

  data class CardUnselected(val card: Card) : RegisterFlipInteraction()

  object OnBackPressed : RegisterFlipInteraction()

  object HeroWonFlip : RegisterFlipInteraction()

  object VillainWonFlip : RegisterFlipInteraction()
}