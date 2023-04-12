package com.mctech.pokergrinder.design.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mctech.pokergrinder.design.compose.typoProvider

@Composable
fun TextField(
  text: String,
  value: MutableState<TextFieldValue>,
  onValueChange: (TextFieldValue) -> Unit = {},
  singleLine: Boolean = true,
  keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
  OutlinedTextField(
    label = {
      Text(text = text)
    },
    value = value.value,
    textStyle = typoProvider().body2,
    singleLine = singleLine,
    onValueChange = {
      value.value = it
      onValueChange(it)
    },
    keyboardOptions = keyboardOptions,
    modifier = Modifier.fillMaxWidth(),
  )

  // Draw a space
  Spacer(modifier = Modifier.height(6.dp))
}