package bowstring.net

import bowstring.packet.*
import io.netty.buffer.*
import io.netty.channel.*
import io.netty.channel.ChannelHandler.*
import io.netty.handler.codec.*

@Sharable
internal object Encoder : MessageToByteEncoder<Packet>() {

	override fun encode(ctx: ChannelHandlerContext, msg: Packet, out: ByteBuf) {
		Packets.encode(msg, out)
	}

}