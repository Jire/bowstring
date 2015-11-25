package bowstring.packet.incoming.decode

import bowstring.net.*
import bowstring.packet.*
import bowstring.packet.incoming.*

object LoginDecoder : PacketDecoder<Login> {

	override fun id() = 16

	override fun decode(client: Client, buf: PacketBuffer): Login {
		val blockLength = buf.uByte()
		val magic = buf.uByte()
		val version = buf.short()
		val highMem = buf.boolean()
		val crcs = IntArray(9)
		for (i in 0..crcs.size - 1) crcs[i] = buf.int()
		val rsaLength = buf.uByte()
		val rsaOpcode = buf.uByte()
		val clientKey = buf.long()
		val serverKey = buf.long()
		val uid = buf.int()
		val username = buf.string()
		val password = buf.string()
		return Login(blockLength, magic, version, highMem, crcs, rsaLength, rsaOpcode,
				clientKey, serverKey, uid, username, password)
	}

}