package bowstring.packet.outgoing.encode

import bowstring.packet.*
import bowstring.packet.outgoing.*

object HandshakeResponseEncoder : PacketEncoder<HandshakeResponse> {

	override fun encode(packet: HandshakeResponse, buf: PacketBuffer) {
		buf.long(8).byte(0).long(packet.serverKey)
	}

}