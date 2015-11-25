package bowstring.packet.outgoing.encode

import bowstring.packet.*
import bowstring.packet.outgoing.*

object PlayerUpdateEncoder : PacketEncoder<PlayerUpdate> {

	override fun id() = 81
	override fun length() = VAR_SHORT

	override fun encode(packet: PlayerUpdate, buf: PacketBuffer) {
		buf.bitAccess()
	}

}