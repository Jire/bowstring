package bowstring.packet.outgoing.encode

import bowstring.packet.*
import bowstring.packet.outgoing.*

object MapRegionEncoder : PacketEncoder<MapRegion> {

	override fun id() = 73

	override fun encode(packet: MapRegion, buf: PacketBuffer) {
		val loc = packet.location
		buf.shortA(loc.regionX() + 6).shortA(loc.regionY() + 6)
	}

}