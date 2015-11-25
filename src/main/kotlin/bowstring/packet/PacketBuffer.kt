package bowstring.packet

import io.netty.buffer.*

// TODO change this to Apollo-style

class PacketBuffer(data: ByteBuf) : DefaultByteBufHolder(data) {

	fun byte() = content().readByte()

	fun uByte() = content().readUnsignedByte()

	fun short() = content().readShort()

	fun uShort() = content().readUnsignedShort()

	fun int() = content().readInt()

	fun uInt() = content().readUnsignedInt()

	fun long() = content().readLong()

	fun float() = content().readFloat()

	fun double() = content().readDouble()

	fun boolean() = content().readBoolean()

	fun string(): String {
		val builder = StringBuilder()
		while (true) {
			val ch = byte().toChar()
			if (ch == '\n') break
			builder.append(ch)
		}
		return builder.toString()
	}

	fun byte(byte: Int): PacketBuffer {
		content().writeByte(byte)
		return this
	}

	fun short(short: Int): PacketBuffer {
		content().writeShort(short)
		return this
	}

	fun int(int: Int): PacketBuffer {
		content().writeInt(int)
		return this
	}

	fun long(long: Long): PacketBuffer {
		content().writeLong(long)
		return this
	}

	fun float(float: Float): PacketBuffer {
		content().writeFloat(float)
		return this
	}

	fun double(double: Double): PacketBuffer {
		content().writeDouble(double)
		return this
	}

	fun string(string: String): PacketBuffer {
		content().writeBytes(string.toByteArray())
		content().writeByte('\n'.toInt())
		return this
	}

	fun boolean(boolean: Boolean): PacketBuffer {
		content().writeBoolean(boolean)
		return this
	}

	fun shortA(short: Int) = byte(short shr 8).byteA(short)

	fun shortA_LE(short: Int) = byteA(short).byte(short shr 8)

	fun byteA(byte: Int) = byte(byte + 128)

	fun bitAccess(): PacketBuffer {
		return this
	}

	private enum class Transformation(val tf: (Int) -> Int) {
		NONE({ it }),
		A({ it + 128 }),
		C({ -it }),
		S({ it - 128 })
	}

}