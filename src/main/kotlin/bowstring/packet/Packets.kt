package bowstring.packet

import bowstring.net.*
import io.netty.buffer.*
import net.jodah.typetools.*
import org.reflections.*
import java.util.*

object Packets {

	private val encoders = HashMap<Class<*>, PacketEncoder<*>>(256)
	private val decoders = arrayOfNulls<PacketDecoder<*>>(256)

	fun <T : Packet> encode(packet: T, buf: ByteBuf) {
		val encoder = (encoders[packet.javaClass] ?: return) as PacketEncoder<T>
		val id = encoder.id()
		if (id != HEADLESS) buf.writeByte(encoder.id())
		encoder.encode(packet, PacketBuffer(buf))
		println("Encoded ${encoder.id()} as $packet")
	}

	fun decode(id: Int, client: Client, buf: ByteBuf): Packet? {
		val decoder = decoders[id] ?: return null
		println("Decoding $id")
		try {
			return decoder.decode(client, PacketBuffer(buf))
		} catch (e: Exception) {
			e.printStackTrace()
			return null
		}
	}

	fun register() {
		val ref = Reflections("bowstring.packet") // get this from
		for (pe in ref.getSubTypesOf(PacketEncoder::class.java)) {
			val gen = TypeResolver.resolveRawArgument(PacketEncoder::class.java, pe)
			encoders.put(gen, pe.kotlin.objectInstance as PacketEncoder<*>)
		}
		for (pd in ref.getSubTypesOf(PacketDecoder::class.java)) {
			val pdi = pd.kotlin.objectInstance as PacketDecoder<*>
			decoders[pdi.id()] = pdi
		}
	}

}