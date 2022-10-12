package com.mctech.pokergrinder.grind.presentation.creation

import com.mctech.pokergrinder.architecture.UserInteraction

internal sealed class NewGrindInteraction : UserInteraction {
  data class SaveGrind(val title: String): NewGrindInteraction()
}