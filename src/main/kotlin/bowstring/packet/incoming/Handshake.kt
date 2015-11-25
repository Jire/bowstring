package bowstring.packet.incoming

import bowstring.packet.*

data class Handshake(val nameHash: Int, val serverKey: Long) : Packet