package bowstring.packet.incoming.decode

import bowstring.net.*
import bowstring.packet.*
import bowstring.packet.incoming.*

object HandshakeDecoder : PacketDecoder<Handshake> {

	override fun id() = 14

	override fun decode(client: Client, buf: PacketBuffer) =
			Handshake(nameHash = buf.byte().toInt(), serverKey = 0)

}