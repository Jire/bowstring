package bowstring.world

data class Location private constructor(val x: Int, val y: Int, val z: Int = 0) {

	companion object {
		fun at(x: Int, y: Int, z: Int = 0) = Location(x, y, z)
	}

	fun regionX() = (x shr 3) - 6

	fun regionY() = (y shr 3) - 6

}