package com.mctech.pokergrinder.tournaments.list

import com.mctech.pokergrinder.architecture.UserInteraction

public sealed class TournamentListInteraction : UserInteraction {

  public data class NewFilterQuery(
    val text: String,
  ) : TournamentListInteraction()

}