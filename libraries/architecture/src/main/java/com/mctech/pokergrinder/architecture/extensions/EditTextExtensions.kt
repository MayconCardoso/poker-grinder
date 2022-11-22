package com.mctech.pokergrinder.architecture.extensions

import android.widget.EditText
import androidx.core.widget.doOnTextChanged

/**
 * Observe given fields content and inform client when all of them have been filled.
 */
inline fun List<EditText>.onDataFormFilled(crossinline callback: (allSet: Boolean) -> Unit) = forEach { field ->
  // Observe field text content.
  field.doOnTextChanged { _, _, _, _ ->
    // Checks if all fields are set.
    val isAllSet = all { field -> field.text.toString().isNotBlank() }

    // Delegate value to client.
    callback(isAllSet)
  }
}