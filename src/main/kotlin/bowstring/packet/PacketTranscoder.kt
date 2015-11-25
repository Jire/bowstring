package bowstring.packet

const val HEADLESS = -1

const val VAR_BYTE = -1
const val VAR_SHORT = -2

interface PacketTranscoder {

	fun id() = HEADLESS

	fun length() = HEADLESS

}