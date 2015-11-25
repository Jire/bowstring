package bowstring.packet

interface PacketEncoder<T : Packet> : PacketTranscoder {

	fun encode(packet: T, buf: PacketBuffer)

}