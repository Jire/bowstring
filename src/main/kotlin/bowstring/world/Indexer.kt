package bowstring.world

class Indexer<E : Mob>(val capacity: Int, val minIndex: Int = 0) : Iterable<E> {

	private val arr = arrayOfNulls<Any?>(capacity)

	var size = 0
		private set
	private var highest = 0

	private val iterator = IndexerIterator()

	fun nextIndex(): Int {
		for (i in minIndex..arr.size) if (arr[i] == null) return i
		throw IllegalStateException("Out of indices!")
	}

	operator fun get(index: Int): E? = arr[index] as E?

	operator fun set(index: Int, element: E?): E {
		val last = get(index)
		arr[index] = element
		if (last == null && element != null) {
			size++
			if (highest < index)
				highest = index
		} else if (last != null && element == null) {
			size--
			if (highest == index)
				highest--
		}
		return last as E
	}

	fun add(element: E): Int {
		val index = nextIndex()
		set(index, element)
		return index
	}

	fun remove(element: E) {
		for (i in minIndex..(highest + 1)) {
			if (element.equals(arr[i])) {
				set(i, null)
				return
			}
		}
	}

	fun contains(element: E): Boolean {
		for (e in this) {
			if (e.equals(element))
				return true
		}
		return false
	}

	override fun iterator(): Iterator<E> {
		iterator.pointer = 0 // resets pointer on each call
		return iterator
	}

	private inner class IndexerIterator : Iterator<E> {

		internal var pointer = minIndex

		override fun next() = arr[pointer++] as E

		override fun hasNext() = size > 0 && pointer <= highest

	}

}