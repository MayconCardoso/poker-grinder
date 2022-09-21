package com.mctech.common_test

/**
 * Just to facilitate data assertion we can make it randomly in future or even
 */
abstract class TestDataFactory<T> {
  protected abstract fun createEntity(): T

  fun single() = createEntity()

  fun list(countItems: Int) = mutableListOf<T>().apply {
    repeat(countItems) {
      add(createEntity())
    }
  }
}
