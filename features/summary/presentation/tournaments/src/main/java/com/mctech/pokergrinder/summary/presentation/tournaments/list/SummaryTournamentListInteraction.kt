package com.mctech.pokergrinder.summary.presentation.tournaments.list

import com.mctech.pokergrinder.architecture.UserInteraction

internal sealed class SummaryTournamentListInteraction : UserInteraction {
  data class NewFilterQuery(val text: String): SummaryTournamentListInteraction()
}