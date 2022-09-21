package com.mctech.pokergrinder.architecture.utility

import com.mctech.pokergrinder.architecture.extensions.takeLast
import java.util.LinkedList
import java.util.Queue

/**
 * Simple implementation of a FIFO [Queue], that uses linked list to store the values. Once the list
 * reaches the given [capacity], it drops the oldest element.
 *
 * This is not a thread safe implementation.
 *
 * @param capacity max number of elements supported in this list.
 */
class CircularLinkedList<T>(private val capacity: Int) : LinkedList<T>() {

  init {
    if (capacity < 0) {
      throw IllegalArgumentException("capacity must be greater or equals 0")
    }
  }

  override fun add(element: T): Boolean {
    cleanHeadForNewEntries()
    return super.add(element)
  }

  override fun add(index: Int, element: T) {
    cleanHeadForNewEntries()
    super.add(index, element)
  }

  override fun addAll(elements: Collection<T>): Boolean {
    cleanHeadForNewEntries(elements.size)
    return super.addAll(elements.takeLast(capacity))
  }

  override fun addAll(index: Int, elements: Collection<T>): Boolean {
    cleanHeadForNewEntries(elements.size)
    return super.addAll(index, elements.takeLast(capacity))
  }

  override fun addFirst(e: T) {
    cleanHeadForNewEntries()
    super.addFirst(e)
  }

  override fun addLast(e: T) {
    cleanHeadForNewEntries()
    super.addLast(e)
  }

  override fun offer(e: T): Boolean {
    cleanHeadForNewEntries()
    return super.offer(e)
  }

  override fun offerFirst(e: T): Boolean {
    cleanHeadForNewEntries()
    return super.offerFirst(e)
  }

  override fun offerLast(e: T): Boolean {
    cleanHeadForNewEntries()
    return super.offerLast(e)
  }

  private fun cleanHeadForNewEntries(newEntriesCount: Int = 1) {
    if (newEntriesCount >= capacity) {
      clear()
    } else while (size + newEntriesCount > capacity) {
      remove()
    }
  }
}
