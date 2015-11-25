package bowstring.task

import java.util.*

object Tasks {

	private val tasks = ArrayDeque<Task>()

	fun process() {
		val iterator = tasks.iterator()
		while (iterator.hasNext()) {
			val task = iterator.next()
			if (task.finish()) iterator.remove()
		}
	}

	fun add(task: Task) {
		tasks.add(task)
	}

	inline fun immediate(crossinline runnable: () -> Unit) = add(object : Task {
		override fun finish(): Boolean {
			runnable.invoke()
			return true
		}
	})

	inline fun delayed(ticks: Int = 1, crossinline runnable: () -> Unit) = add(object : TickTask(ticks) {
		override fun run() {
			runnable.invoke()
			stop()
		}
	})

	inline fun repeating(ticks: Int = 1, crossinline task: () -> Boolean) = add(object : TickTask(ticks) {
		override fun run() {
			if (task.invoke())
				stop()
		}
	})

	inline fun continuous(ticks: Int = 1, crossinline runnable: () -> Unit) = repeating(ticks) {
		runnable.invoke()
		true
	}

}