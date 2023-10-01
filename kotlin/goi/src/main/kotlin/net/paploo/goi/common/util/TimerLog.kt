package net.paploo.goi.common.util

import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger

class TimerLog(
    val name: String,
    private val handleMark: (Mark) -> Unit = {}
) {

    val id: Int = counter.incrementAndGet()

    private val markLog: MutableList<Mark> = mutableListOf()

    private val startTime: Long = System.nanoTime()

    fun mark(label: String): Mark =
        mark { label }

    fun mark(label: (Duration) -> String): Mark {
        val deltaNanos = System.nanoTime() - startTime
        val delta = Duration.ofNanos(deltaNanos)
        return Mark(
            label = label(delta),
            delta = delta,
            owner = this
        ).also { mark ->
            synchronized(markLog) { markLog += mark }
        }.also {
            handleMark(it)
        }
    }

    inline fun <R> markAround(label: String, block: () -> R): R {
        val startMark = mark("> start: $label")
        return block().also {
            mark {
                val deltaNanos = (it - startMark.delta).toNanos()
                val deltaFormatted = String.format("%.6f", (deltaNanos / 1000000.0))
                "< end  : $label ($deltaFormatted ms)"
            }
        }
    }

    fun marks(): List<Mark> = synchronized(markLog) {
        mutableListOf<Mark>().apply { addAll(markLog) }.sorted().toList()
    }

    fun formatted(): String =
        marks().joinToString(
            separator = "\n",
            prefix = "${this::class.simpleName}(id = $id, name = $name) {\n",
            postfix = "\n}"
        ) {
            "\t${it.formatted()}"
        }

    override fun toString(): String = "${this::class.simpleName}(id = $id, name = $name)"

    data class Mark(
        val label: String,
        val delta: Duration,
        private val owner: TimerLog
    ) : Comparable<Mark> {

        override fun compareTo(other: Mark): Int = delta compareTo other.delta

        fun formatted(): String {
            val timeInMillis = String.format("%.6f", (delta.toNanos() / 1000000.0))
            return "[${timeInMillis.padStart(12)} ms] ${owner.name} #${owner.id} - $label"
        }

    }

    companion object {
        private val counter = AtomicInteger()
    }
}