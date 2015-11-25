package bowstring.packet.outgoing.encode

import bowstring.packet.*
import bowstring.packet.outgoing.*

object PlayerDetailsEncoder : PacketEncoder<PlayerDetails> {

	override fun id() = 249

	override fun encode(packet: PlayerDetails, buf: PacketBuffer) {
		buf.byteA(packet.membership).shortA_LE(packet.index).byte(107)
	}

}