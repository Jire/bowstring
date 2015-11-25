package bowstring

import bowstring.net.*
import bowstring.packet.*
import bowstring.task.*
import bowstring.world.*
import java.util.concurrent.*
import java.util.concurrent.Executors.*

const val CYCLE_MS = 600L

fun main(args: Array<String>) {
	registerPackets()
	newSingleThreadScheduledExecutor().scheduleAtFixedRate({
		process()
		World.process()
	}, CYCLE_MS, CYCLE_MS, TimeUnit.MILLISECONDS)
	Server.start()
}