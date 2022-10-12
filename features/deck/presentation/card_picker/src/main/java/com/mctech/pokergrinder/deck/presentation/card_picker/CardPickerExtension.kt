package com.mctech.pokergrinder.deck.presentation.card_picker

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mctech.pokergrinder.deck.domain.Card

public fun Card.loadCardImage(context: Context): Drawable? = formattedName().loadCardImage(context)

public fun String.loadCardImage(context: Context): Drawable? = ContextCompat.getDrawable(
  context,
  context.resources.getIdentifier("card_${this}", "drawable", context.packageName)
)