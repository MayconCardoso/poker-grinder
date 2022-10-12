package com.mctech.pokergrinder.tournament.presentation.list_component

import com.mctech.pokergrinder.architecture.UserInteraction

public sealed class TournamentListInteraction : UserInteraction {

  public data class NewFilterQuery(
    val text: String,
  ) : TournamentListInteraction()

}