package com.mctech.chart.money

public data class MoneyVariationEntry(
  val amount: Double,
)

internal data class MoneyVariationView(
  val data: MoneyVariationEntry,
  var y: Float,
)