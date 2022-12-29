package com.mctech.pokergrinder.settings.domain.entities

/**
 * Defines a settings entity.
 *
 * @property key settings key.
 * @property value settings value.
 * @property title settings title.
 */
data class Settings(
  val key: String,
  val value: String,
  val title: String,
) {
  /**
   * Converts settings as a boolean value.
   */
  fun asBoolean() = value.toBooleanStrict()
}

/**
 * Checks if a specific settings is enabled.
 */
fun List<Settings>.isEnabled(settingsAvailable: SettingsAvailable) = firstOrNull {
  it.key == settingsAvailable.key
}?.asBoolean() == true