package com.mctech.pokergrinder.settings.domain.entities

import com.mctech.pokergrinder.settings.testing.newSettings
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SettingsTest {
  private val enabledSettings = newSettings(value = "true", key = "group_session_tournaments")
  private val disabledSettings = newSettings(value = "false")
  private val invalidSettings = newSettings(value = "1")
  private val all = listOf(enabledSettings, disabledSettings)

  @Test
  fun `should return true for enabled and false for disabled`() {
    assertThat(enabledSettings.asBoolean()).isTrue
    assertThat(disabledSettings.asBoolean()).isFalse
  }

  @Test(expected = IllegalArgumentException::class)
  fun `should throw error`() {
    invalidSettings.asBoolean()
  }

  @Test
  fun `should check enabled list`() {
    assertThat(all.isEnabled(SettingsAvailable.GROUP_TOURNAMENTS)).isTrue
  }
}