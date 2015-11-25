package bowstring.world

import bowstring.world.player.*

const val PLAYER_CAPACITY = 2000

object World {

	private val players = Indexer<Player>(PLAYER_CAPACITY)

	fun process() {
		for (player in players) player.process()
	}

}