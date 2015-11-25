package bowstring.packet.outgoing

import bowstring.packet.*

data class LoginResponse(val status: Int, val rights: Int, val flagged: Boolean,
                         val index: Int, val member: Boolean) : Packet