package bowstring.world.player

import bowstring.net.*
import bowstring.world.*

class Player(index: Int, val client: Client, val details: PlayerDetails) : Mob(index) {

	override fun process() {
		client.flush()
	}

}