package com.mctech.pokergrinder.tournament.domain.entities

import java.io.Serializable

/**
 * Declares the available tournament types.
 */
enum class TournamentType: Serializable {
  HYPER,
  TURBO,
  REGULAR,
  SLOW,
}