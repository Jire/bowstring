package bowstring.packet.outgoing.encode

import bowstring.packet.*
import bowstring.packet.outgoing.*

object LoginResponseEncoder : PacketEncoder<LoginResponse> {

	override fun encode(packet: LoginResponse, buf: PacketBuffer) {
		buf.byte(packet.status).byte(packet.rights).boolean(packet.flagged)
				.short(packet.index).boolean(packet.member)
	}

}