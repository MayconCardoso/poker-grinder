package com.mctech.pokergrinder.architecture.extensions

import java.util.NoSuchElementException

/**
 * If [Collection] has more elements than the given [numberOfElements], returns a sub list
 * containing only the last [numberOfElements].
 *
 * @param numberOfElements max number of elements to be returned, should be a positive value
 * @throws [NoSuchElementException] if [numberOfElements] is negative
 */
fun <T> Collection<T>.takeLast(numberOfElements: Int): Collection<T> {
  if (numberOfElements < 0) {
    throw NoSuchElementException()
  }
  return if (size > numberOfElements) {
    val startIndex = size - numberOfElements
    filterIndexed { index, _ -> index >= startIndex }
  } else {
    this
  }
}

/**
 * Checks if [Collection] is not empty. If the list is empty, an [IllegalArgumentException] will be thrown.
 *
 * @param message to be displayed in the exception.
 * @throws IllegalArgumentException if the [Collection] is empty.
 */
fun <T> Collection<T>.requireNotEmpty(message: String) {
  if (isEmpty()) throw IllegalArgumentException(message)
}
