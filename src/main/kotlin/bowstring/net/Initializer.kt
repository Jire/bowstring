package bowstring.net

import io.netty.channel.*
import io.netty.channel.ChannelHandler.*
import io.netty.channel.socket.*

@Sharable
internal object Initializer : ChannelInitializer<SocketChannel>() {

	override fun initChannel(ch: SocketChannel) {
		ch.pipeline().addLast(Encoder, Decoder(), Handler())
	}

}