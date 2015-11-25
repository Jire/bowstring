package bowstring.net

import io.netty.bootstrap.*
import io.netty.channel.*
import io.netty.channel.epoll.*
import io.netty.channel.nio.*
import io.netty.channel.socket.nio.*

internal const val DEFAULT_PORT = 43594

object Server {

	fun start(port: Int = DEFAULT_PORT) {
		val epoll = Epoll.isAvailable()
		val parentGroup = if (epoll) EpollEventLoopGroup(1) else NioEventLoopGroup(1)
		val childGroup = if (epoll) EpollEventLoopGroup() else NioEventLoopGroup()
		val channel: Class<out ServerChannel> = if (epoll) EpollServerSocketChannel::class.java
												else NioServerSocketChannel::class.java
		ServerBootstrap()
				.group(parentGroup, childGroup)
				.channel(channel)
				.childHandler(Initializer)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(port)
		println("Bound to port $port")
	}

}