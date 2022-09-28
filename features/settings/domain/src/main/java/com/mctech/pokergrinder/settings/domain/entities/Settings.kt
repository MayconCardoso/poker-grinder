package com.mctech.pokergrinder.settings.domain.entities

data class Settings(
  val key: String,
  val value: String,
  val title: String,
) {
  fun asBoolean() = value.toBooleanStrict()
}

fun List<Settings>.isEnabled(settingsAvailable: SettingsAvailable) = firstOrNull {
  it.key == settingsAvailable.key
}?.asBoolean() == true