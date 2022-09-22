package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction
import com.mctech.pokergrinder.tournaments.domain.entities.Tournament

internal sealed class NewGrindInteraction : UserInteraction {

  data class SaveGrind(
    val title: String,
  ): NewGrindInteraction()


}