package com.mctech.pokergrinder.settings.testing

import com.mctech.pokergrinder.settings.domain.entities.Settings

/**
 * Creates a new settings for test purpose.
 */
fun newSettings(
  key: String = "0",
  value: String = "0",
  title: String = "0",
) = Settings(
  key = key,
  value = value,
  title = title,
)