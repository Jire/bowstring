package bowstring.packet.outgoing

import bowstring.packet.*

data class HandshakeResponse(val serverKey: Long) : Packet