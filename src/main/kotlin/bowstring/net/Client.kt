package bowstring.net

import bowstring.packet.*
import io.netty.channel.*

class Client(val channel: Channel) {

	fun write(packet: Packet): Client {
		channel.write(packet)
		return this
	}

	fun flush() = channel.flush()

	fun destroy() = channel.close()

}