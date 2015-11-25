package bowstring.net

import bowstring.packet.incoming.*
import bowstring.packet.outgoing.*
import bowstring.world.*
import io.netty.channel.*
import io.netty.util.*

val ATTR_CLIENT = AttributeKey.valueOf<Client>("client")

internal class Handler : ChannelHandlerAdapter() {

	override fun handlerRemoved(ctx: ChannelHandlerContext) {
		ctx.attr(ATTR_CLIENT).get().destroy()
		super.handlerRemoved(ctx)
	}

	override fun channelRegistered(ctx: ChannelHandlerContext) {
		println("Received connection from ${ctx.channel().remoteAddress()}")
		super.channelRegistered(ctx)
	}

	override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
		val client = ctx.attr(ATTR_CLIENT).get()
		when (msg) {
			is Handshake -> client.write(HandshakeResponse(msg.serverKey)).flush()
			is Login -> {
				client.write(LoginResponse(2, 2, false, 1, true))
						.write(PlayerDetails(1, 1))
						.write(MapRegion(Location.at(3222, 3222)))
						.flush()
			}
			else -> println("Invalid msg ${msg.javaClass.simpleName}")
		}
		super.channelRead(ctx, msg)
	}

	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
		ctx.channel().close()
	}

}