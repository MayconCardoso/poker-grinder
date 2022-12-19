package com.mctech.pokergrinder.formatter

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Holds an instance of formatter classes to speed up its usage.
 */
internal object Formatters {
  val decimal = DecimalFormat(
    "$#0.00",
    DecimalFormatSymbols(Locale.ENGLISH)
  )

  val date = DateFormat.getDateInstance()
}

/**
 * Formats a double onto a formatted currency string.
 */
fun Double.asFormattedCurrency() = Formatters.decimal.format(this)

/**
 * Formats a double onto a formatted currency string.
 */
fun Float.asFormattedCurrency() = Formatters.decimal.format(this)

/**
 * Formats a Long onto a formatted date string.
 */
fun Long.asFormattedDate() = Formatters.date.format(this)