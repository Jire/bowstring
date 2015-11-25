package bowstring.packet

import bowstring.net.*

interface PacketDecoder<out T : Packet> : PacketTranscoder {

	fun decode(client: Client, buf: PacketBuffer): T

}