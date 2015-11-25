package bowstring.packet.incoming

import bowstring.packet.*

data class Login(val blockLength: Short, val magic: Short, val version: Short, val highMem: Boolean,
                 val crcs: IntArray, val rsaLength: Short, val rsaOpcode: Short, val clientKey: Long, val serverKey: Long,
                 val uid: Int, val username: String, val password: String) : Packet