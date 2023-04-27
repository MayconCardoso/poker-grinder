package com.mctech.pokergrinder.settings.domain.entities

/**
 * Known settings.
 */
enum class SettingsAvailable(val key: String) {

  /**
   * Used to group tournaments with the same title for better seeing it on the session screen.
   */
  GROUP_TOURNAMENTS("group_session_tournaments"),

  /**
   * Used to show or hide balance on the balance component.
   */
  HIDE_BANKROLL_BALANCE("hide_bankroll_balance")
}