package bowstring.net

import bowstring.packet.*
import io.netty.buffer.*
import io.netty.channel.*
import io.netty.handler.codec.*

internal class Decoder : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		var client = ctx.channel().attr(ATTR_CLIENT).get()
		if (client == null) {
			client = Client(ctx.channel())
			ctx.channel().attr(ATTR_CLIENT).set(client)
		}

		while (buf.isReadable) {
			val id = buf.readUnsignedByte().toInt()
			println("Received packet ID $id")
			val packet = decode(id, client, buf)
			out.add(packet as Any)
			println("Received packet $packet")
		}
	}

}